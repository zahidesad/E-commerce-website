package com.service;

import com.model.*;
import com.repository.OrderRepository;
import com.repository.ProductRepository;
import com.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderService.
 * Spring context NOT started; repositories are mocked.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepo;
    @Mock
    ProductRepository productRepo;

    @InjectMocks
    OrderService orderService;
    @Mock
    StockRepository stockRepository;

    private CartItem cartItem(long prodId, int qty, BigDecimal price) {
        Product p = new Product();
        p.setId(prodId);

        CartItem ci = new CartItem();
        ci.setProduct(p);
        ci.getProduct().setId(prodId);
        ci.setQuantity(qty);
        ci.setPrice(price);
        ci.setTotal(price.multiply(BigDecimal.valueOf(qty)));
        return ci;
    }

    /* -------------------------------------------------------
     * 1) convertCartItemsToOrderItems
     * ----------------------------------------------------- */
    @Test
    @DisplayName("convertCartItemsToOrderItems copies quantity, price, total")
    void convert_items() {
        CartItem ci1 = cartItem(1, 2, new BigDecimal("10"));
        CartItem ci2 = cartItem(2, 1, new BigDecimal("15"));
        Order order = new Order();

        List<OrderItem> items = orderService.convertCartItemsToOrderItems(
                List.of(ci1, ci2), order);

        assertThat(items).hasSize(2);
        assertThat(items.get(0).getQuantity()).isEqualTo(2);
        assertThat(items.get(0).getTotal()).isEqualByComparingTo("20");
        assertThat(items.get(0).getOrder()).isSameAs(order);
    }

    /* -------------------------------------------------------
     * 2) saveOrder passes to repository
     * ----------------------------------------------------- */
    @Test
    void saveOrder() {
        Order o = new Order();
        when(orderRepo.save(o)).thenAnswer(inv -> {
            o.setId(77L);
            return o;
        });

        orderService.saveOrder(o);

        assertThat(o.getId()).isEqualTo(77L);
        verify(orderRepo).save(o);
    }

    /* -------------------------------------------------------
     * 3) updateOrderStatus
     * ----------------------------------------------------- */
    @Nested
    class UpdateStatus {

        Order order;
        OrderItem oi;
        Product product;

        @BeforeEach
        void setup() {
            product = new Product();
            product.setId(1L);
            product.setStock(5);
            oi = new OrderItem();
            oi.setProduct(product);
            oi.setQuantity(3);
            order = new Order();
            order.setId(88L);
            order.setStatus("Pending");
            order.setOrderItems(List.of(oi));

            when(orderRepo.findById(88L)).thenReturn(Optional.of(order));
            lenient().when(productRepo.findById(1L)).thenReturn(Optional.of(product));
            when(orderRepo.save(order)).thenReturn(order);
        }

        @Test
        void approve_with_sufficient_stock() {
            orderService.updateOrderStatus(88L, "Approved");

            assertThat(order.getStatus()).isEqualTo("Approved");
            assertThat(product.getStock()).isEqualTo(2);      // 5 - 3
            verify(productRepo).save(product);
        }

        @Test
        void approve_with_insufficient_stock_throws() {
            product.setStock(2);   // not enough

            assertThatThrownBy(() -> orderService.updateOrderStatus(88L, "Approved"))
                    .isInstanceOf(RuntimeException.class);
            assertThat(order.getStatus()).isEqualTo("Approved"); // unchanged
            verify(productRepo, never()).save(product);
        }

        @Test
        void non_approved_status_does_not_touch_stock() {
            orderService.updateOrderStatus(88L, "Shipped");

            assertThat(order.getStatus()).isEqualTo("Shipped");
            verify(productRepo, never()).save(any());
        }
    }
}
