package org.example.cybersecurity_platform.controller;

import org.example.cybersecurity_platform.model.Purchase;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.PurchaseRepository;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public ProfileController(UserRepository userRepository,
                             PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        // 1️⃣ load the User entity
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No user: " + username));

        // 2️⃣ load their purchases
        List<Purchase> purchases = purchaseRepository.findByUser(user);

        // 3️⃣ add both to the model
        model.addAttribute("user", user);
        model.addAttribute("purchases", purchases);

        return "profile";
    }
}
