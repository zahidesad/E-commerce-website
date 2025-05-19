package com.service;

import com.model.*;
import com.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProductService.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepo;
    @Mock
    PriceRepository priceRepo;
    @Mock
    StockRepository stockRepo;
    @Mock
    CategoryRepository categoryRepo;   // or CategoryService
    @Mock
    CartService cartService;

    @InjectMocks
    ProductService productService;

    /* ---------- helpers ---------- */
    private Product prod(long id, String name) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        return p;
    }

    private Price price(Product p, BigDecimal amt) {
        Price pr = new Price();
        pr.setProduct(p);
        pr.setPrice(amt);
        pr.setStartDate(new Date(System.currentTimeMillis() - 86400000)); // yesterday
        pr.setEndDate(new Date(System.currentTimeMillis() + 86400000));   // tomorrow
        return pr;
    }

    private Stock stock(Product p, int qty) {
        Stock s = new Stock();
        s.setProduct(p);
        s.setQuantity(qty);
        return s;
    }

    /* ---------------------------------------------------
     * 1) getAllProducts
     * ------------------------------------------------- */
    @Test
    void getAllProducts_sets_currentPrice() {
        Product a = prod(1, "Apple");
        a.setPrices(List.of(price(a, new BigDecimal("5.0"))));
        Product b = prod(2, "Banana");
        b.setPrices(List.of(price(b, new BigDecimal("3.0"))));
        when(productRepo.findAll()).thenReturn(List.of(a, b));

        List<Product> list = productService.getAllProducts();

        assertThat(list).hasSize(2);
        assertThat(list.get(0).getCurrentPrice()).isEqualByComparingTo("5.0");
        assertThat(list.get(1).getCurrentPrice()).isEqualByComparingTo("3.0");
    }

    /* ---------------------------------------------------
     * 2) getProductById
     * ------------------------------------------------- */
    @Test
    void getProductById_valid() {
        Product p = prod(10, "Desk");
        p.setPrices(List.of(price(p, new BigDecimal("100"))));
        p.setStock(5);
        when(productRepo.findById(10L)).thenReturn(Optional.of(p));

        Optional<Product> result = productService.getProductById(10L);

        assertThat(result).isPresent();
        assertThat(result.get().getCurrentPrice()).isEqualByComparingTo("100");
    }

    @Test
    void getProductById_invalid_returns_empty() {
        when(productRepo.findById(99L)).thenReturn(Optional.empty());

        assertThat(productService.getProductById(99L)).isEmpty();
    }

    /* ---------------------------------------------------
     * 3) searchProducts
     * ------------------------------------------------- */
    @Test
    void searchProducts_filters_by_name_case_insensitive() {
        Product a = prod(1,"Laptop Pro");
        a.setPrices(List.of(price(a,new BigDecimal("800"))));

        lenient().when(productRepo.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(List.of(a));           // lenient stub

        List<Product> res = productService.searchProducts("LaP");

        verify(productRepo).findByNameContainingIgnoreCase("LaP");  // behaviour check
        assertThat(res).singleElement()
                .extracting(Product::getName).isEqualTo("Laptop Pro");
    }

    /* ---------------------------------------------------
     * 4) isProductInStock
     * ------------------------------------------------- */
    @Test
    void isProductInStock_true_when_qty_positive() {
        Product p = prod(5, "Chair");
        p.setStock(2);
        when(productRepo.findById(5L)).thenReturn(Optional.of(p));

        assertThat(productService.isProductInStock(5L)).isTrue();
    }

    @Test
    void isProductInStock_false_when_no_stock() {
        when(productRepo.findById(6L)).thenReturn(Optional.empty());

        assertThat(productService.isProductInStock(6L)).isFalse();
    }

    /* ---------------------------------------------------
     * 5) saveProduct
     * ------------------------------------------------- */
    @Test
    void saveProduct_persists_product_price_stock_categories() {
        Category cat = new Category(); cat.setId(1L);
        lenient().when(categoryRepo.findById(1L)).thenReturn(Optional.of(cat));
        lenient().when(productRepo.save(any(Product.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Product newProd = prod(0,"Tablet");

        productService.saveProduct(newProd,
                new BigDecimal("250"),10,new Date(),null,List.of(1L));
    }


    /* ---------------------------------------------------
     * 6) deleteProductById
     * ------------------------------------------------- */
    @Test
    void deleteProductById_cleans_cart_and_deletes() {
        productService.deleteProductById(44L);

        verify(cartService).deleteCartItemByProductId(44L);
        verify(productRepo).deleteById(44L);
    }
}
