package org.example.cybersecurity_platform.service;

import org.example.cybersecurity_platform.model.Role;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomUserDetailsService();
        userDetailsService.userRepository = userRepository; // field injection
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        User mockUser = new User();
        mockUser.setUsername("rares");
        mockUser.setPassword("encodedPassword");
        mockUser.setRoles(Set.of(Role.ROLE_LEARNER, Role.ROLE_BLOGGER));

        when(userRepository.findByUsername("rares")).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername("rares");

        assertEquals("rares", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_LEARNER")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_BLOGGER")));
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("ghost");
        });
    }
}