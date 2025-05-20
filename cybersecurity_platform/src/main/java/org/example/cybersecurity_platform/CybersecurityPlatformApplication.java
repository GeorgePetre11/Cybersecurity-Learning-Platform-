package org.example.cybersecurity_platform;

import org.example.cybersecurity_platform.model.Challenge;
import org.example.cybersecurity_platform.model.Course;
import org.example.cybersecurity_platform.model.Role;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.CourseRepository;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.example.cybersecurity_platform.repository.ChallengeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

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


    @Bean
    public CommandLineRunner seedCourses(CourseRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Course("Intro to Cybersecurity",
                        "A first look at threats, defenses & best practices.",
                        49.99, 20));
                repo.save(new Course("Web App Pentesting",
                        "Hands-on hacking of web applications.",
                        99.00, 10));
                repo.save(new Course("Secure Coding in Java",
                        "Avoid the OWASP Top 10 via secure patterns.",
                        79.50, 15));
            }
        };
    }


    @Bean
    CommandLineRunner seedChallenges(ChallengeRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Challenge(null, "Networking Beginner Challenge", 10));
                repo.save(new Challenge(null, "Networking Advanced Challenge", 5));
            }
        };
    }


}
