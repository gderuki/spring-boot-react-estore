package lv.psanatovs.api.controller;


import lv.psanatovs.api.dto.ProductInfoResponseDTO;
import lv.psanatovs.api.exception.ProductNotFoundException;
import lv.psanatovs.api.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ProductGraphQLController {
    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<ProductInfoResponseDTO> getAllProducts() {
        return productService.findAll();
    }

    @QueryMapping
    public ProductInfoResponseDTO getProduct(@Argument String id) {
        return
                productService
                        .findById(id)
                        .orElseThrow(
                                () -> new ProductNotFoundException(id)
                        );
    }
}
