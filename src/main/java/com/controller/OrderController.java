package com.controller;

import com.model.Cart;
import com.model.Order;
import com.model.OrderItem;
import com.service.CartService;
import com.service.OrderService;
import com.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private StripeService stripeService;



    @PostMapping("/completeOrder")
    public String completeOrder(HttpSession session, @RequestParam("addressId") Long addressId,
                                @RequestParam("paymentMethod") String paymentMethod,
                                @RequestParam("totalAmount") double totalAmount,
                                @RequestParam(value = "stripeToken", required = false) String stripeToken, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        System.out.println("Address ID: " + addressId);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Stripe Token: " + stripeToken);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(new Date());
        order.setDeliveryDate(getDeliveryDate(7));
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Pending");
        order.setAddressId(addressId);

        String transactionId = UUID.randomUUID().toString();
        order.setTransactionId(transactionId);

        if ("Credit Card".equals(paymentMethod)) {
            try {
                int amountInCents = (int) (totalAmount * 100);
                String chargeId = stripeService.createCharge(stripeToken, amountInCents);
                order.setTransactionId(chargeId);
                order.setStatus("Approved");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("msg", "Payment failed: " + e.getMessage());
                return "orderDetails";
            }
        }

        Cart cart = cartService.getOrCreateCartByUserId(userId);
        List<OrderItem> orderItems = orderService.convertCartItemsToOrderItems(cart.getCartItems(), order);

        order.setOrderItems(orderItems);
        orderService.saveOrder(order);
        cartService.clearCartByUserId(userId);

        model.addAttribute("order", order);
        return "orderConfirmation";
    }



    private Date getDeliveryDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }

    @GetMapping("/myOrders")
    public String viewMyOrders(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while retrieving your orders.");
            return "error";
        }
        return "myOrders";
    }

    @GetMapping("/viewDetails")
    public String viewOrderDetails(@RequestParam("orderId") Long orderId, Model model) {
        try {
            Order order = orderService.getOrderById(orderId);
            model.addAttribute("order", order);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while retrieving the order details.");
            return "error";
        }
        return "viewDetails";
    }

    @GetMapping("/admin/orders")
    public String viewAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "adminOrders";
    }

    @GetMapping("/admin/orderDetails")
    public String adminViewOrderDetails(@RequestParam("id") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "adminOrderDetails";
    }

    @PostMapping("/admin/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/orders";
    }



}
