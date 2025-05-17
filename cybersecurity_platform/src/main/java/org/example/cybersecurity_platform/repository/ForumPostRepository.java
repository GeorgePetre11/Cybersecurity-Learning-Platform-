package org.example.cybersecurity_platform.repository;

import org.example.cybersecurity_platform.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}

