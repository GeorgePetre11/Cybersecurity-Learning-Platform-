// src/main/java/org/example/cybersecurity_platform/config/CartAdvice.java
package org.example.cybersecurity_platform.config;

import org.example.cybersecurity_platform.service.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CartAdvice {
    private final CartService cartService;

    public CartAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartService")
    public CartService cartService() {
        return cartService;
    }
}
