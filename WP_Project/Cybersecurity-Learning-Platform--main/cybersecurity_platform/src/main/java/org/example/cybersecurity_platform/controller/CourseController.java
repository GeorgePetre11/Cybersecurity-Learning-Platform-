// src/main/java/org/example/cybersecurity_platform/controller/CourseController.java
package org.example.cybersecurity_platform.controller;

import org.example.cybersecurity_platform.model.Course;
import org.example.cybersecurity_platform.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    private final CourseRepository courseRepo;

    public CourseController(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    // List all courses
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "courses/list";
    }

    // Show one course’s detail
    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No course with id " + id));
        model.addAttribute("course", course);
        return "courses/detail";
    }
}
