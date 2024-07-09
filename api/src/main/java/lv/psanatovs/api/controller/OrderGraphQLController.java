package lv.psanatovs.api.controller;

import lv.psanatovs.api.graphql.types.ProductInput;
import lv.psanatovs.api.service.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

public class OrderGraphQLController {
    private final OrderService orderService;

    public OrderGraphQLController(OrderService orderService) {
        this.orderService = orderService;
    }

    @MutationMapping
    public Boolean placeOrder(@Argument ProductInput product) {
        return orderService.placeOrder(product);
    }
}
