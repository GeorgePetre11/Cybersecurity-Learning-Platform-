package org.example.cybersecurity_platform.repository;

import org.example.cybersecurity_platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // you can add custom queries here if needed, e.g.:
    // List<Course> findByTitleContainingIgnoreCase(String keyword);
}
