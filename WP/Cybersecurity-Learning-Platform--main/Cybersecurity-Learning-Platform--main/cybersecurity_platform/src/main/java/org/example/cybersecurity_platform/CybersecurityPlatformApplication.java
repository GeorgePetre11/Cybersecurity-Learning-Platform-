package org.example.cybersecurity_platform;

import org.example.cybersecurity_platform.model.Role;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class CybersecurityPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CybersecurityPlatformApplication.class, args);
    }

    // ← Paste this bean below your main() method
    @Bean
    CommandLineRunner createAdmin(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                //admin.setEmail("admin@example.com");    // if you added email
                admin.setPassword(encoder.encode("changeMe123"));
                admin.setRoles(Set.of(Role.ROLE_ADMIN));
                userRepo.save(admin);
                System.out.println("🔐 Created default admin/changeMe123 — change the password ASAP");
            }
        };
    }
}
