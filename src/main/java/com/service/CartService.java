package com.service;

import com.model.Cart;
import com.model.Product;
import com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public List<Cart> getCartByEmail(String email) {
        List<Cart> cartItems = cartRepository.findByEmail(email);

        return cartItems.stream().map(cartItem -> {
            Optional<Product> product = productService.getProductById((long) cartItem.getProductId());
            product.ifPresent(p -> cartItem.setProductName(p.getName()));
            return cartItem;
        }).collect(Collectors.toList());
    }


    public void addCart(Cart cart) {
        Optional<Cart> existingCartItem = cartRepository.findByEmailAndProductId(cart.getEmail(), cart.getProductId());
        if (existingCartItem.isPresent()) {
            Cart item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + cart.getQuantity());
            item.setTotal(item.getQuantity() * item.getPrice());
            cartRepository.save(item);
        } else {
            cart.setTotal(cart.getQuantity() * cart.getPrice());
            cartRepository.save(cart);
        }
    }


    public void updateCartQuantity(int id, String action) {
        Optional<Cart> cartItemOpt = cartRepository.findById((long) id);
        if (cartItemOpt.isPresent()) {
            Cart cartItem = cartItemOpt.get();
            if ("inc".equals(action)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else if ("dec".equals(action) && cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            } else {
                cartRepository.deleteById((long) id);
                return;
            }
            cartItem.setTotal(cartItem.getPrice() * cartItem.getQuantity());
            cartRepository.save(cartItem);
        }
    }

    public void removeFromCart(int id) {
        cartRepository.deleteById((long) id);
    }


    public void clearCartByEmail(String email) {
        List<Cart> carts = cartRepository.findByEmail(email);
        cartRepository.deleteAll(carts);
    }
}
