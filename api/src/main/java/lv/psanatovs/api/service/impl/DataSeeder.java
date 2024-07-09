package lv.psanatovs.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lv.psanatovs.api.dto.*;
import lv.psanatovs.api.entity.Category;
import lv.psanatovs.api.entity.ImageGallery;
import lv.psanatovs.api.entity.Product;
import lv.psanatovs.api.entity.attribute.AttributeItem;
import lv.psanatovs.api.entity.attribute.AttributeSet;
import lv.psanatovs.api.entity.attribute.AttributeSetType;
import lv.psanatovs.api.entity.price.Currency;
import lv.psanatovs.api.entity.price.Price;
import lv.psanatovs.api.repository.CategoryRepository;
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

    private final ProductRepository productRepository; // Assuming you have a repository
    private final AttributeSetRepository attributeSetRepository;
    private final AttributeSetTypeRepository attributeSetTypeRepository;
    private final AttributeItemRepository attributeItemRepository;
    private final CurrencyRepository currencyRepository;
    private final PriceRepository priceRepository;
    private final ImageGalleryRepository imageGalleryRepository;
    private final CategoryRepository categoryRepository;

    public DataSeeder(
            ProductRepository productRepository,
            AttributeSetRepository attributeSetRepository,
            AttributeSetTypeRepository attributeSetTypeRepository,
            AttributeItemRepository attributeItemRepository,
            CurrencyRepository currencyRepository,
            PriceRepository priceRepository,
            ImageGalleryRepository imageGalleryRepository,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.attributeSetRepository = attributeSetRepository;
        this.attributeSetTypeRepository = attributeSetTypeRepository;
        this.attributeItemRepository = attributeItemRepository;
        this.currencyRepository = currencyRepository;
        this.priceRepository = priceRepository;
        this.imageGalleryRepository = imageGalleryRepository;
        this.categoryRepository = categoryRepository;
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

            for (CategoryDTO categoryDTO : json.data().categories()) {
                 categoryRepository.save(mapCategoryDTOToEntity(categoryDTO));
            }

            for (ProductDTO productDTO : json.data().products()) {
                var product = mapProductDTOToEntity(productDTO);
                productRepository.save(product);

                for (PriceDTO priceDTO : productDTO.prices()) {
                    var price = mapPriceDTOToEntity(priceDTO, product);
                    priceRepository.save(price);
                }

                for (String url : productDTO.gallery()) {
                    var imageGallery = new ImageGallery();
                    imageGallery.setImageUrl(url);
                    imageGallery.setProduct(product);
                    imageGalleryRepository.save(imageGallery);
                }

                for (AttributeSetDTO attributeSetDTO : productDTO.attributes()) {
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

    private static Category mapCategoryDTOToEntity(CategoryDTO category) {
        return new Category(category.name());
    }

    private static Product mapProductDTOToEntity(ProductDTO productDTO) {
        var product = new Product();

        product.setName(productDTO.name());
        product.setInStock(productDTO.inStock());
        product.setDescription(productDTO.description());
        product.setCategory(productDTO.category());

        return product;
    }

    private Price mapPriceDTOToEntity(PriceDTO priceDTO, Product product) {
        var price = new Price();

        price.setAmount(BigDecimal.valueOf(priceDTO.amount()));
        price.setProduct(product);
        var currency = currencyRepository.findByCode(priceDTO.currency().label())
                .orElseGet(() -> {
                    var newCurrency = new Currency();
                    newCurrency.setCode(priceDTO.currency().label());
                    newCurrency.setSymbol(priceDTO.currency().symbol());
                    return currencyRepository.save(newCurrency); // This ensures the Currency is managed
                });
        price.setCurrency(currency);

        return price;
    }

    private AttributeSet mapAttributeSetDTOToEntity(AttributeSetDTO attributeSetDTO, Product product) {
        var attributeSet = new AttributeSet();

        var type = attributeSetTypeRepository.findByName(attributeSetDTO.name())
                .orElseGet(() ->
                        {
                            var typeEntity = new AttributeSetType(attributeSetDTO.name());
                            return attributeSetTypeRepository.save(typeEntity);
                        }
                );
        attributeSet.setProduct(product);
        attributeSet.setType(type);
        var savedAttributeSet = attributeSetRepository.save(attributeSet);
        for (AttributeItemDTO itemDTO : attributeSetDTO.items()) {
            AttributeItem item = new AttributeItem();
            item.setValue(itemDTO.value());
            item.setDisplayValue(itemDTO.displayValue());
            item.setAttributeSet(savedAttributeSet);
            attributeItemRepository.save(item);
        }

        return savedAttributeSet;
    }
}
