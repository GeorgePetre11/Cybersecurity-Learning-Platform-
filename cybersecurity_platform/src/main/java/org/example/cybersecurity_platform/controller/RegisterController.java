// src/main/java/org/example/cybersecurity_platform/controller/RegisterController.java
package org.example.cybersecurity_platform.controller;

import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.model.Role;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RegisterController {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository users,
                              PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(name = "roles", required = false) List<String> rolesParam,
            Model model
    ) {
        // 1) Check if username already exists
        if (users.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }

        // 2) Map submitted strings to Role enum (or default to LEARNER)
        Set<Role> roles;
        if (rolesParam == null || rolesParam.isEmpty()) {
            roles = Set.of(Role.ROLE_LEARNER);
        } else {
            roles = rolesParam.stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
        }

        // 3) Create & save new user
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRoles(roles);

        users.save(u);
        return "redirect:/login?registered";
    }
}
