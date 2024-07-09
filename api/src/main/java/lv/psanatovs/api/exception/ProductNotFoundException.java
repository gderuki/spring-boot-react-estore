package lv.psanatovs.api.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final String errorId;

    public ProductNotFoundException(String productId) {
        super("Product not found with id: " + productId);
        this.errorId = UUID.randomUUID().toString();
    }
}