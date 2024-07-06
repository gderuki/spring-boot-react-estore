package lv.psanatovs.api.service;

import lv.psanatovs.api.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(String kebabCaseName);
}

