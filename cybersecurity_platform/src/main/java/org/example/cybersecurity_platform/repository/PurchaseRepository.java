// src/main/java/org/example/cybersecurity_platform/repository/PurchaseRepository.java
package org.example.cybersecurity_platform.repository;

import org.example.cybersecurity_platform.model.Purchase;
import org.example.cybersecurity_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByUser(User user);
}
