package lv.psanatovs.api.service;

import lv.psanatovs.api.graphql.types.ProductInput;

public interface OrderService {
    Boolean placeOrder(ProductInput product);
}
