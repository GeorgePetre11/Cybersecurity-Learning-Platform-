package org.example.cybersecurity_platform.repository;

import org.example.cybersecurity_platform.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // no methods needed yet
}
