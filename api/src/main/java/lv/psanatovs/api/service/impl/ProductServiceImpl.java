package lv.psanatovs.api.service.impl;

import lv.psanatovs.api.dto.*;
import lv.psanatovs.api.entity.ImageGallery;
import lv.psanatovs.api.entity.Product;
import lv.psanatovs.api.entity.attribute.AttributeSet;
import lv.psanatovs.api.entity.price.Price;
import lv.psanatovs.api.exception.ProductNotFoundException;
import lv.psanatovs.api.repository.ImageGalleryRepository;
import lv.psanatovs.api.repository.ProductRepository;
import lv.psanatovs.api.repository.attribute.AttributeItemRepository;
import lv.psanatovs.api.repository.attribute.AttributeSetRepository;
import lv.psanatovs.api.repository.price.PriceRepository;
import lv.psanatovs.api.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final AttributeSetRepository attributeRepository;
    private final AttributeItemRepository attributeItemRepository;
    private final ImageGalleryRepository imageGalleryRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            PriceRepository priceRepository,
            AttributeSetRepository attributeRepository,
            AttributeItemRepository attributeItemRepository,
            ImageGalleryRepository imageGalleryRepository
    ) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.attributeRepository = attributeRepository;
        this.attributeItemRepository = attributeItemRepository;
        this.imageGalleryRepository = imageGalleryRepository;
    }

    @Override
    public List<ProductInfoResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::modelToDto)
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public Optional<ProductInfoResponseDTO> findById(String productId) {
        var productOpt = productRepository.findByKebabCaseName(productId);

        if (productOpt.isEmpty()) {
            throw new ProductNotFoundException("Product not found for id: " + productId);
        }

        return modelToDto(productOpt.get());
    }

    private Optional<ProductInfoResponseDTO> modelToDto(Product product) {
        var dto = new ProductInfoResponseDTO();
        dto.setId(product.getKebabCaseName());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setInStock(product.isInStock());

        // prices
        var prices = priceRepository.findByProductId(product.getId())
                .stream()
                .map(this::priceModelToDto)
                .toList();
        dto.setPrices(prices);

        // attributes
        var attributes =
                attributeRepository.findByProductId(product.getId())
                        .stream()
                        .map(this::attributeSetModelToDto)
                        .toList();
        dto.setAttributes(attributes);

        // gallery
        var gallery = imageGalleryRepository.findByProductId(product.getId())
                .stream()
                .map(this::imageGalleryModelToDto)
                .toList();
        dto.setGallery(gallery);

        return Optional.of(dto);
    }

    private PriceDTO priceModelToDto(Price price) {
        var dto = new PriceDTO();

        var currency =
                new CurrencyDTO(
                        price.getCurrency().getCode(),
                        price.getCurrency().getSymbol()
                );
        dto.setCurrency(currency);
        dto.setAmount(price.getAmount().doubleValue());

        return dto;
    }

    private AttributeSetDTO attributeSetModelToDto(AttributeSet attributeSet) {
        var dto = new AttributeSetDTO();

        dto.setName(attributeSet.getType().getName());
        dto.setItems(attributeItemRepository
                .findByAttributeSetId(attributeSet.getId()).stream().map(
                attributeItem -> new AttributeItemDTO(
                        attributeItem.getValue(),
                        attributeItem.getDisplayValue()
                )
        ).toList());

        return dto;
    }

    private String imageGalleryModelToDto(ImageGallery imageGallery) {
        return imageGallery.getImageUrl();
    }
}