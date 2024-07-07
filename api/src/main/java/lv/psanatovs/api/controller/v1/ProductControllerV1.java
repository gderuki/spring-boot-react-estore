package lv.psanatovs.api.controller.v1;

import lv.psanatovs.api.dto.ProductInfoResponseDTO;
import lv.psanatovs.api.exception.ProductNotFoundException;
import lv.psanatovs.api.response.ApiResponse;
import lv.psanatovs.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductControllerV1 {

    private final ProductService productService;

    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ProductInfoResponseDTO>>> findAll() {
        return
                new ResponseEntity<>(
                        new ApiResponse<>(
                                true,
                                productService.findAll()),
                        HttpStatus.OK
                );
    }

    @GetMapping(value = "/{kebabCaseName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ProductInfoResponseDTO>> findById(@PathVariable String kebabCaseName) {
        return
                new ResponseEntity<>(
                        new ApiResponse<>(
                                true,
                                productService
                                        .findById(kebabCaseName)
                                        .orElseThrow(() -> new ProductNotFoundException(kebabCaseName))
                        ),
                        HttpStatus.OK
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        return
                new ResponseEntity<>(
                        new ApiResponse<>(
                                false,
                                "Product not found"),
                        HttpStatus.NOT_FOUND
                );
    }
}