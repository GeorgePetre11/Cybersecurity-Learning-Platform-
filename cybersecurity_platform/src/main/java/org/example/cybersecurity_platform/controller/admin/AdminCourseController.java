package org.example.cybersecurity_platform.controller.admin;

import org.example.cybersecurity_platform.model.Course;
import org.example.cybersecurity_platform.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseRepository courseRepo;

    public AdminCourseController(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    // 1️⃣ List all courses
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "admin/courses/list";   // create this template
    }

    // 2️⃣ Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/courses/form";
    }

    // 3️⃣ Handle create
    @PostMapping("/create")
    public String createCourse(@ModelAttribute Course course) {
        courseRepo.save(course);
        return "redirect:/admin/courses";
    }

    // 4️⃣ Show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id).orElseThrow();
        model.addAttribute("course", course);
        return "admin/courses/form";
    }

    // 5️⃣ Handle update
    @PostMapping("/{id}/edit")
    public String updateCourse(
            @PathVariable Long id,
            @ModelAttribute Course form
    ) {
        Course c = courseRepo.findById(id).orElseThrow();
        c.setTitle(form.getTitle());
        c.setDescription(form.getDescription());
        c.setPrice(form.getPrice());
        c.setQuantity(form.getQuantity());
        courseRepo.save(c);
        return "redirect:/admin/courses";
    }

    // 6️⃣ Delete a course
    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return "redirect:/admin/courses";
    }
}
