package com.service;

import com.model.Cart;
import com.model.CartItem;
import com.model.Order;
import com.model.OrderItem;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void createOrderFromCart(Cart cart) {
        Order order = new Order();
        order.setUserId(cart.getUser().getId());
        order.setOrderDate(new Date());
        order.setStatus("Pending");

        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotal(cartItem.getTotal());

            orderItemRepository.save(orderItem);
        }
    }
}
