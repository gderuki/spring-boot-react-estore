package lv.psanatovs.api.service;

import lv.psanatovs.api.dto.ProductInfoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductInfoResponseDTO> findAll();
    Optional<ProductInfoResponseDTO> findById(String kebabCaseName);
}

