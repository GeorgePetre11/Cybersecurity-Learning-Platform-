package org.example.cybersecurity_platform.service;

import org.example.cybersecurity_platform.model.Course;
import org.example.cybersecurity_platform.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    private CourseRepository courseRepo;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        courseRepo = Mockito.mock(CourseRepository.class);
        cartService = new CartService(courseRepo);
    }

    @Test
    void addIncrementsQuantity() {
        cartService.add(42L);
        cartService.add(42L);
        assertEquals(2, cartService.getTotalItems());
    }

    @Test
    void getTotalPriceCalculatesCorrectly() {
        Course c = new Course();
        c.setId(100L);
        c.setPrice(50.0);
        Mockito.when(courseRepo.findById(100L)).thenReturn(Optional.of(c));

        cartService.add(100L);
        cartService.add(100L);

        assertEquals(100.0, cartService.getTotalPrice(), 0.001);
    }

    @Test
    void getItemsFiltersMissingCourses() {
        cartService.add(1L);
        cartService.add(2L);
        Course c1 = new Course(); c1.setId(1L); c1.setPrice(10.0);

        Mockito.when(courseRepo.findById(1L)).thenReturn(Optional.of(c1));
        Mockito.when(courseRepo.findById(2L)).thenReturn(Optional.empty());

        Map<Course, Integer> items = cartService.getItems();

        assertTrue(items.containsKey(c1));
        assertFalse(items.keySet().stream().anyMatch(course -> course.getId().equals(2L)));
    }

    @Test
    void clearEmptiesCart() {
        cartService.add(5L);
        cartService.clear();
        assertTrue(cartService.isEmpty());
    }
}