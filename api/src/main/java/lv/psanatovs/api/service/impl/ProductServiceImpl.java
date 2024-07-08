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
            throw new ProductNotFoundException(productId);
        }

        return modelToDto(productOpt.get());
    }

    private Optional<ProductInfoResponseDTO> modelToDto(Product product) {
        // prices
        var prices = priceRepository.findByProductId(product.getId())
                .stream()
                .map(this::priceModelToDto)
                .toList();

        // attributes
        var attributes = attributeRepository.findByProductId(product.getId())
                .stream()
                .map(this::attributeSetModelToDto)
                .toList();

        // gallery
        var gallery = imageGalleryRepository.findByProductId(product.getId())
                .stream()
                .map(this::imageGalleryModelToDto)
                .toList();

        var dto = new ProductInfoResponseDTO(
                product.getKebabCaseName(),
                product.getName(),
                product.isInStock(),
                product.getDescription(),
                product.getCategory(),
                attributes,
                prices,
                gallery
        );

        return Optional.of(dto);
    }

    private PriceDTO priceModelToDto(Price price) {
        var currency =
                new CurrencyDTO(
                        price.getCurrency().getCode(),
                        price.getCurrency().getSymbol()
                );

        return new PriceDTO(
                price.getAmount().doubleValue(),
                currency
        );
    }

    private AttributeSetDTO attributeSetModelToDto(AttributeSet attributeSet) {
        return new AttributeSetDTO(
                attributeSet.getType().getName(),
                attributeItemRepository
                        .findByAttributeSetId(attributeSet.getId()).stream().map(
                                attributeItem -> new AttributeItemDTO(
                                        attributeItem.getValue(),
                                        attributeItem.getDisplayValue()
                                )
                        ).toList()
        );
    }

    private String imageGalleryModelToDto(ImageGallery imageGallery) {
        return imageGallery.getImageUrl();
    }
}