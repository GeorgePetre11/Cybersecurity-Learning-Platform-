package org.example.cybersecurity_platform.controller.admin;

import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.example.cybersecurity_platform.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepository userRepo;

    public AdminUserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // 1️⃣ List all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin/users/list";  // create this Thymeleaf template next
    }

    // 2️⃣ Show edit form for a single user
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        model.addAttribute("user", user);
        // provide all possible roles for checkboxes in the form
        model.addAttribute("allRoles", Role.values());
        return "admin/users/form";  // create this template
    }

    // 3️⃣ Handle the form submission to update roles
    @PostMapping("/{id}/edit")
    public String updateUserRoles(
            @PathVariable Long id,
            @RequestParam(required = false) List<Role> roles
    ) {
        User user = userRepo.findById(id).orElseThrow();
        user.getRoles().clear();
        if (roles != null) {
            user.getRoles().addAll(roles);
        }
        userRepo.save(user);
        return "redirect:/admin/users";
    }
}
