package lv.psanatovs.api.service.impl;

import lv.psanatovs.api.entity.Order;
import lv.psanatovs.api.graphql.types.ProductInput;
import lv.psanatovs.api.repository.OrderRepository;
import lv.psanatovs.api.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Boolean placeOrder(ProductInput product) {
        try {
            var order = convertToOrderEntity(product);
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Order convertToOrderEntity(ProductInput product) {
        return
                new Order(
                        product.productId(),
                        product.quantity()
                );
    }
}
