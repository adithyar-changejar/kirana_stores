package com.example.kiranastore.service;

import com.example.kiranastore.entity.OrderStatus;
import com.example.kiranastore.mongo.CartDocument;
import com.example.kiranastore.mongo.CartItem;
import com.example.kiranastore.mongo.OrderDocument;
import com.example.kiranastore.mongo.OrderItem;
import com.example.kiranastore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The type Order service.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository the order repository
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create order order document.
     *
     * @param cart  the cart
     * @param total the total
     * @return the order document
     */
    public OrderDocument createOrder(CartDocument cart, BigDecimal total) {

        List<OrderItem> items = cart.getItems().stream().map(this::toOrderItem).toList();

        OrderDocument order = new OrderDocument();
        order.setUserId(cart.getUserId());
        order.setStoreId(cart.getStoreId());
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(new Date());

        return orderRepository.save(order);
    }

    private OrderItem toOrderItem(CartItem cartItem) {
        OrderItem item = new OrderItem();
        item.setProductId(cartItem.getProductId());
        item.setQuantity(cartItem.getQuantity());
        item.setPrice(cartItem.getPriceSnapshot());
        item.setSubtotal(
                cartItem.getPriceSnapshot()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );
        return item;
    }
}
