// src/main/java/org/example/cybersecurity_platform/model/Challenge.java
package org.example.cybersecurity_platform.model;

import jakarta.persistence.*;

@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Challenge() { }
    public Challenge(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
