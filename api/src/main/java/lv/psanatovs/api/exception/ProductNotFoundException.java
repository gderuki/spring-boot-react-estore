package lv.psanatovs.api.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String kebabCaseName) {
        super("Product not found with id: " + kebabCaseName);
    }
}