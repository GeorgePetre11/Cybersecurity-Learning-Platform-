package org.example.cybersecurity_platform.controller;


import org.example.cybersecurity_platform.model.ForumComment;
import org.example.cybersecurity_platform.model.ForumPost;
import org.example.cybersecurity_platform.model.User;
import org.example.cybersecurity_platform.repository.ForumCommentRepository;
import org.example.cybersecurity_platform.repository.ForumPostRepository;
import org.example.cybersecurity_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;



import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumPostRepository postRepo;

    @Autowired
    private ForumCommentRepository commentRepo;

    @Autowired
    private UserRepository userRepo;

    // Show list of forum posts
    @GetMapping
    public String forumHome(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        return "forum/index";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model, HttpServletRequest request) {
        ForumPost post = postRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        List<ForumComment> comments = commentRepo.findByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        // 🔒 CSRF token
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        if (csrf != null) {
            model.addAttribute("_csrf", csrf);
        }

        return "forum/post";
    }

    // Handle comment submission
    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             Principal principal) {
        ForumPost post = postRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ForumComment comment = new ForumComment();
        comment.setContent(content);
        comment.setAuthor(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepo.save(comment);
        return "redirect:/forum/post/" + id;
    }

    // Show form to create a new post
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new ForumPost());
        return "forum/new";
    }

    // Handle new post submission
    @PostMapping("/new")
    public String createPost(@ModelAttribute ForumPost post, Principal principal) {
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));
        post.setAuthor(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepo.save(post);
        return "redirect:/forum";
    }

    @PostMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        ForumComment comment = commentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        User currentUser = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.name().equals("ADMIN") || role.name().equals("ROLE_ADMIN"));

        boolean isOwner = comment.getAuthor().getUsername().equals(principal.getName());

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You are not allowed to delete this comment");
        }

        Long postId = comment.getPost().getId();
        commentRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Comment deleted.");
        return "redirect:/forum/post/" + postId;
    }


}

