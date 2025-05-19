// src/main/java/org/example/cybersecurity_platform/controller/CartController.java
package org.example.cybersecurity_platform.controller;

import org.example.cybersecurity_platform.model.Purchase;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.PurchaseRepository;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.example.cybersecurity_platform.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cart;
    private final PurchaseRepository purchases;
    private final UserRepository users;

    public CartController(CartService cart, PurchaseRepository purchases, UserRepository users) {
        this.cart = cart;
        this.purchases = purchases;
        this.users = users;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartService", cart); // <== Add this line
        return "cart/view";
    }



    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {
        cart.add(id);
        return "redirect:/courses";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cart.remove(id);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails ud) {
        User user = users.findByUsername(ud.getUsername()).orElseThrow();
        cart.getItems().forEach((course, qty) -> {
            for (int i = 0; i < qty; i++) {
                Purchase p = new Purchase();
                p.setUser(user);
                p.setCourse(course);
                purchases.save(p);
            }
            // decrement stock
            course.setQuantity(course.getQuantity() - qty);
        });
        cart.clear();
        return "redirect:/profile";
    }
}
