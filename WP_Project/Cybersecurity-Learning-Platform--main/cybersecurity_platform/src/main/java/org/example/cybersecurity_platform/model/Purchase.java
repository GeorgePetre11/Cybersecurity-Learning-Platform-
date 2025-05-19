// src/main/java/org/example/cybersecurity_platform/model/Purchase.java
package org.example.cybersecurity_platform.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant purchasedAt;

    // … getters …

    public void setUser(User user) {
        this.user = user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // no setter for purchasedAt
}
