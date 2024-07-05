package com.service;

import com.model.Cart;
import com.model.CartItem;
import com.model.Product;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.ProductRepository;
import com.repository.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    public Cart getOrCreateCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.getUser().setId(userId);
            return cartRepository.save(cart);
        });
    }

    public void addProductToCart(Cart cart, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        BigDecimal currentPrice = priceRepository.findCurrentPriceByProductId(productId);

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotal(currentPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
            cartItemRepository.save(item);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(currentPrice);
            cartItem.setTotal(currentPrice.multiply(BigDecimal.valueOf(quantity)));
            cartItemRepository.save(cartItem);
        }
    }


    public void updateCartItemQuantity(Long cartItemId, String action) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if ("inc".equals(action)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else if ("dec".equals(action) && cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cartItemRepository.deleteById(cartItemId);
                return;
            }
            BigDecimal currentPrice = cartItem.getPrice();
            cartItem.setTotal(currentPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart != null) {
            cartItemRepository.deleteByCartId(cart.getId());
        }
    }

    @Transactional
    public void deleteCartItemByProductId(Long productId) {
        cartItemRepository.deleteByProductId(productId);
    }
}
