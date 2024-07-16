package com.service;

import com.model.*;
import com.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotal(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        for (Order order : orders) {
            order.getOrderItems().size();
        }
        return orders;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);

        if ("Approved".equalsIgnoreCase(status)) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product = productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
                int totalStock = product.getStocks().stream().mapToInt(Stock::getQuantity).sum();
                if (totalStock < item.getQuantity()) {
                    throw new RuntimeException("Not enough stock for product: " + product.getName());
                }
                product.getStocks().get(0).setQuantity(product.getStocks().get(0).getQuantity() - item.getQuantity()); // Update the stock
                stockRepository.save(product.getStocks().get(0));
                productRepository.save(product);
            }
        }
    }




}
