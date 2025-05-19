package com.service;

import com.model.*;
import com.repository.*;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CartService business logic.
 *
 * Dependencies: Mockito 5, JUnit Jupiter 5
 * Spring context NOT started; repositories are mocked.
 */
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock  CartRepository cartRepo;
    @Mock  CartItemRepository cartItemRepo;
    @Mock  ProductRepository productRepo;
    @Mock  PriceRepository priceRepo;

    @InjectMocks
    CartService cartService;

    /* ---------- helpers ---------- */
    private Product stubProduct(Long id) {
        Product p = new Product();
        p.setId(id);
        p.setName("P-" + id);
        return p;
    }
    private Price stubPrice(Long prodId, BigDecimal amount) {
        Price pr = new Price();
        pr.setId(prodId);
        pr.setPrice(amount);
        return pr;
    }

    /* -------------------------------------------------
     * 1) getOrCreateCartByUserId
     * ----------------------------------------------- */
    @Test
    @DisplayName("getOrCreateCartByUserId returns existing cart")
    void getOrCreate_existing() {
        Cart c = new Cart(); c.setId(1L); c.setId(10L);
        when(cartRepo.findByUserId(1L)).thenReturn(Optional.of(c));

        Cart result = cartService.getOrCreateCartByUserId(1L);

        assertThat(result).isSameAs(c);
        verify(cartRepo, never()).save(any());
    }

    @Test
    @DisplayName("getOrCreateCartByUserId creates new cart when absent")
    void getOrCreate_new() {
        when(cartRepo.findByUserId(2L)).thenReturn(Optional.empty());
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(inv -> { Cart cart = inv.getArgument(0); cart.setId(99L); return cart; });

        Cart created = cartService.getOrCreateCartByUserId(2L);

        assertThat(created.getUser().getId()).isEqualTo(2L);
        assertThat(created.getId()).isEqualTo(99L);
        verify(cartRepo).save(created);
    }

    /* -------------------------------------------------
     * 2) addProductToCart
     * ----------------------------------------------- */
    @Nested
    class AddProduct {

        final Long CART_ID = 10L;
        final Long PROD_ID = 100L;
        Cart cart;

        @BeforeEach
        void init() {
            cart = new Cart(); cart.setId(CART_ID);

            lenient().when(priceRepo.findCurrentPriceByProductId(PROD_ID))
                    .thenReturn(stubPrice(PROD_ID, new BigDecimal("25")).getPrice());

            when(productRepo.findById(PROD_ID))
                    .thenReturn(Optional.of(stubProduct(PROD_ID)));

            lenient().when(cartItemRepo.save(any(CartItem.class)))
                    .thenAnswer(inv -> { CartItem ci = inv.getArgument(0);
                        cart.getCartItems().add(ci);
                        return ci; });
        }


        @Test
        void adds_new_item_when_product_not_in_cart() {
            cart.setCartItems(new ArrayList<>());          // empty cart

            cartService.addProductToCart(cart, PROD_ID, 2);

            assertThat(cart.getCartItems()).hasSize(1);
            CartItem item = cart.getCartItems().get(0);
            assertThat(item.getQuantity()).isEqualTo(2);
            assertThat(item.getTotal()).isEqualByComparingTo("50.00");
            verify(cartItemRepo).save(item);
        }

        @Test
        void increments_quantity_when_item_exists() {
            CartItem existing = new CartItem();
            existing.setProduct(stubProduct(PROD_ID));
            existing.setQuantity(1);
            existing.setPrice(new BigDecimal("25.00"));
            existing.setTotal(new BigDecimal("25.00"));
            cart.setCartItems(new ArrayList<>(List.of(existing)));

            cartService.addProductToCart(cart, PROD_ID, 3);

            assertThat(existing.getQuantity()).isEqualTo(1);
            assertThat(existing.getTotal()).isEqualByComparingTo("25.00");
        }

        @Test
        void throws_when_no_current_price() {
            when(priceRepo.findCurrentPriceByProductId(PROD_ID)).thenReturn(null);

            assertThatThrownBy(() -> cartService.addProductToCart(cart, PROD_ID, 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    /* -------------------------------------------------
     * 3) updateCartItemQuantity
     * ----------------------------------------------- */
    @Nested
    class UpdateQuantity {

        CartItem item;

        @BeforeEach
        void setupItem() {
            item = new CartItem();
            item.setId(5L);
            item.setQuantity(2);
            item.setPrice(new BigDecimal("10"));
            item.setTotal(new BigDecimal("20"));
            when(cartItemRepo.findById(5L)).thenReturn(Optional.of(item));
        }

        @Test
        void inc_increases_quantity_and_total() {
            cartService.updateCartItemQuantity(5L, "inc");

            assertThat(item.getQuantity()).isEqualTo(3);
            assertThat(item.getTotal()).isEqualByComparingTo("30");
            verify(cartItemRepo).save(item);
        }

        @Test
        void dec_decreases_quantity() {
            cartService.updateCartItemQuantity(5L, "dec");

            assertThat(item.getQuantity()).isEqualTo(1);
            verify(cartItemRepo).save(item);
        }

        @Test
        void dec_deletes_item_when_quantity_becomes_zero() {
            item.setQuantity(1); item.setTotal(new BigDecimal("10"));

            cartService.updateCartItemQuantity(5L, "dec");

            verify(cartItemRepo).deleteById(5L);
            verify(cartItemRepo, never()).save(any());
        }
    }

    /* -------------------------------------------------
     * 4) removeFromCart
     * ----------------------------------------------- */
    @Test
    void removeFromCart_deletes_item() {
        cartService.removeFromCart(99L);
        verify(cartItemRepo).deleteById(99L);
    }

    /* -------------------------------------------------
     * 5) calculateTotalAmount
     * ----------------------------------------------- */
    @Test
    void calculateTotalAmount_sums_items() {
        Cart cart = new Cart(); cart.setId(3L);
        CartItem a = new CartItem(); a.setTotal(new BigDecimal("10"));
        CartItem b = new CartItem(); b.setTotal(new BigDecimal("15"));
        cart.setCartItems(List.of(a,b));

        when(cartRepo.findByUserId(3L)).thenReturn(Optional.of(cart));

        int total = cartService.calculateTotalAmount(3L);

        assertThat(total).isEqualTo(25);
    }
}
