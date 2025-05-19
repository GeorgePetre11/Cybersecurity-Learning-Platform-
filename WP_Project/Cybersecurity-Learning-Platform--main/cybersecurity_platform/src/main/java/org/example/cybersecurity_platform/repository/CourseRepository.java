package org.example.cybersecurity_platform.repository;

import org.example.cybersecurity_platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);
}
