//package org.example.cybersecurity_platform.repository;
//
//import org.example.cybersecurity_platform.model.Role;
//import org.example.cybersecurity_platform.model.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//public class UserRepositoryIntegrationTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testSaveAndFindUserByUsername() {
//        // Arrange
//        User user = new User();
//        user.setUsername("dbtestuser");
//        user.setPassword("securepassword");
//        user.setRoles(Set.of(Role.ROLE_CHALLENGER));
//
//        userRepository.save(user);
//
//        // Act
//        Optional<User> retrieved = userRepository.findByUsername("dbtestuser");
//
//        // Assert
//        assertTrue(retrieved.isPresent());
//        assertEquals("dbtestuser", retrieved.get().getUsername());
//        assertTrue(retrieved.get().getRoles().contains(Role.ROLE_CHALLENGER));
//    }
//}