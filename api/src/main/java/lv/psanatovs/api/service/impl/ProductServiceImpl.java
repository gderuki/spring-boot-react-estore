package lv.psanatovs.api.service.impl;

import lv.psanatovs.api.entity.Product;
import lv.psanatovs.api.exception.ProductNotFoundException;
import lv.psanatovs.api.repository.ProductRepository;
import lv.psanatovs.api.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(String kebabCaseName) {
        return productRepository.findByKebabCaseName(kebabCaseName);
    }
}