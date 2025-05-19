// src/main/java/org/example/cybersecurity_platform/service/CartService.java
package org.example.cybersecurity_platform.service;

import org.example.cybersecurity_platform.model.Course;
import org.example.cybersecurity_platform.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.AbstractMap;
import java.util.stream.Collectors;

@Service
@SessionScope
public class CartService {

    private final CourseRepository courseRepo;
    private final Map<Long, Integer> items = new LinkedHashMap<>();

    public CartService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    /**
     * Returns a map of Course → quantity for the items in the cart.
     */
    public Map<Course, Integer> getItems() {
        return items.entrySet().stream()
                .map(e -> {
                    return courseRepo.findById(e.getKey())
                            .map(c -> new AbstractMap.SimpleEntry<>(c, e.getValue()))
                            .orElse(null);
                })
                .filter(entry -> entry != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    /**
     * Add one unit of the given course to the cart.
     */
    public void add(Long courseId) {
        items.merge(courseId, 1, Integer::sum);
    }

    /**
     * Remove the given course entirely from the cart.
     */
    public void remove(Long courseId) {
        items.remove(courseId);
    }

    /**
     * Clear all items from the cart.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Returns true if the cart has no items.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Total quantity of all items in the cart.
     */
    public int getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Total price of all items in the cart.
     */
    public double getTotalPrice() {
        return getItems().entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }
}
