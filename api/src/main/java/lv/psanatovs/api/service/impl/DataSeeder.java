package lv.psanatovs.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lv.psanatovs.api.dto.*;
import lv.psanatovs.api.entity.ImageGallery;
import lv.psanatovs.api.entity.Product;
import lv.psanatovs.api.entity.attribute.AttributeItem;
import lv.psanatovs.api.entity.attribute.AttributeSet;
import lv.psanatovs.api.entity.attribute.AttributeSetType;
import lv.psanatovs.api.entity.price.Currency;
import lv.psanatovs.api.entity.price.Price;
import lv.psanatovs.api.repository.ImageGalleryRepository;
import lv.psanatovs.api.repository.attribute.AttributeItemRepository;
import lv.psanatovs.api.repository.attribute.AttributeSetRepository;
import lv.psanatovs.api.repository.attribute.AttributeSetTypeRepository;
import lv.psanatovs.api.repository.ProductRepository;
import lv.psanatovs.api.repository.price.CurrencyRepository;
import lv.psanatovs.api.repository.price.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

@Service
public class DataSeeder {
    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);


    @PersistenceContext
    private final EntityManager entityManager;
    private final ProductRepository productRepository; // Assuming you have a repository
    private final AttributeSetRepository attributeSetRepository;
    private final AttributeSetTypeRepository attributeSetTypeRepository;
    private final AttributeItemRepository attributeItemRepository;
    private final CurrencyRepository currencyRepository;
    private final PriceRepository priceRepository;
    private final ImageGalleryRepository imageGalleryRepository;

    public DataSeeder(
            EntityManager entityManager, ProductRepository productRepository,
            AttributeSetRepository attributeSetRepository,
            AttributeSetTypeRepository attributeSetTypeRepository,
            AttributeItemRepository attributeItemRepository,
            CurrencyRepository currencyRepository,
            PriceRepository priceRepository,
            ImageGalleryRepository imageGalleryRepository
    ) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.attributeSetRepository = attributeSetRepository;
        this.attributeSetTypeRepository = attributeSetTypeRepository;
        this.attributeItemRepository = attributeItemRepository;
        this.currencyRepository = currencyRepository;
        this.priceRepository = priceRepository;
        this.imageGalleryRepository = imageGalleryRepository;
    }

    @PostConstruct
    public void init() {
        if (isDatabaseEmpty()) {
            populateDatabase();
        }
    }

    private boolean isDatabaseEmpty() {
        return productRepository.count() == 0;
    }

    private void populateDatabase() {
        try {
            var mapper = new ObjectMapper();
            final String SAMPLE_DATA_JSON = "data.json";
            var inputStream = getClass().getClassLoader().getResourceAsStream(SAMPLE_DATA_JSON);
            var json = mapper.readValue(inputStream, DataWrapper.class);

            for (ProductDTO productDTO : json.getData().getProducts()) {
                var product = mapProductDTOToEntity(productDTO);
                productRepository.save(product);

                for (PriceDTO priceDTO : productDTO.getPrices()) {
                    var price = mapPriceDTOToEntity(priceDTO, product);
                    priceRepository.save(price);
                }

                for (String url : productDTO.getGallery()) {
                    var imageGallery = new ImageGallery();
                    imageGallery.setImageUrl(url);
                    imageGallery.setProduct(product);
                    imageGalleryRepository.save(imageGallery);
                }

                for (AttributeSetDTO attributeSetDTO : productDTO.getAttributes()) {
                    var attributeSet = mapAttributeSetDTOToEntity(attributeSetDTO, product);
                    attributeSetRepository.save(attributeSet);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Sample data file not found", e);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON file", e);
        } catch (DataAccessException e) {
            logger.error("Database access error", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
        }
    }

    private static Product mapProductDTOToEntity(ProductDTO productDTO) {
        var product = new Product();

        product.setName(productDTO.name);
        product.setInStock(productDTO.inStock);
        product.setDescription(productDTO.description);
        product.setCategory(productDTO.category);

        return product;
    }

    private Price mapPriceDTOToEntity(PriceDTO priceDTO, Product product) {
        var price = new Price();

        price.setAmount(BigDecimal.valueOf(priceDTO.getAmount()));
        price.setProduct(product);
        var currency = currencyRepository.findByCode(priceDTO.getCurrency().getLabel())
                .orElseGet(() -> {
                    var newCurrency = new Currency();
                    newCurrency.setCode(priceDTO.getCurrency().getLabel());
                    newCurrency.setSymbol(priceDTO.getCurrency().getSymbol());
                    return currencyRepository.save(newCurrency); // This ensures the Currency is managed
                });
        price.setCurrency(currency);

        return price;
    }

    private AttributeSet mapAttributeSetDTOToEntity(AttributeSetDTO attributeSetDTO, Product product) {
        var attributeSet = new AttributeSet();

        var type = attributeSetTypeRepository.findByName(attributeSetDTO.getName())
                .orElseGet(() ->
                        {
                            var typeEntity = new AttributeSetType(attributeSetDTO.getName());
                            return attributeSetTypeRepository.save(typeEntity);
                        }
                );
        attributeSet.setProduct(product);
        attributeSet.setType(type);
        var savedAttributeSet = attributeSetRepository.save(attributeSet);
        for (AttributeItemDTO itemDTO : attributeSetDTO.getItems()) {
            AttributeItem item = new AttributeItem();
            item.setValue(itemDTO.getValue());
            item.setDisplayValue(itemDTO.getDisplayValue());
            // Associate the item with the saved AttributeSet
            item.setAttributeSet(savedAttributeSet);
            attributeItemRepository.save(item);
        }

        return savedAttributeSet;
    }
//    private AttributeSet mapAttributeSetDTOToEntity(AttributeSetDTO attributeSetDTO, Product product) {
//        var attributeSet = new AttributeSet();
//
//        for (AttributeItemDTO itemDTO : attributeSetDTO.getItems()) {
//            AttributeItem item = new AttributeItem();
//            item.setValue(itemDTO.getValue());
//            item.setDisplayValue(itemDTO.getDisplayValue());
//            item.setAttributeSet(attributeSet);
//            attributeItemRepository.save(item);
//        }
//        var type = attributeSetTypeRepository.findByName(attributeSetDTO.getName())
//                .orElseGet(
//                        () ->
//                                attributeSetTypeRepository.save(
//                                        new AttributeSetType(attributeSetDTO.getName())
//                                )
//                );
//        attributeSet.setProduct(product);
//        attributeSet.setType(type);
//
//        return attributeSet;
//    }
}
