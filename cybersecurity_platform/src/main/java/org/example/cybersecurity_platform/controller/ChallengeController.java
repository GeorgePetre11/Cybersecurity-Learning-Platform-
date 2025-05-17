// src/main/java/org/example/cybersecurity_platform/controller/ChallengeController.java
package org.example.cybersecurity_platform.controller;

import org.example.cybersecurity_platform.model.Challenge;
import org.example.cybersecurity_platform.model.Question;
import org.example.cybersecurity_platform.repository.ChallengeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ChallengeController {
    private final ChallengeRepository repo;
    public ChallengeController(ChallengeRepository repo) {
        this.repo = repo;
    }

    // 3.1 List page
    @GetMapping("/challenges")
    public String list(Model model) {
        model.addAttribute("challenges", repo.findAll());
        return "challenges/list";
    }

    // src/main/java/org/example/cybersecurity_platform/controller/ChallengeController.java
    @GetMapping("/challenges/{id}/take")
    public String take(@PathVariable Long id, Model model) {
        Challenge chal = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such challenge"));

        List<Question> questions = List.of(
                new Question(
                        "Which layer of the OSI model does the IP protocol operate?",
                        List.of("Application", "Transport", "Network", "Data Link")
                ),
                new Question(
                        "What is the default port number for HTTP?",
                        List.of("21", "80", "443", "25")
                ),
                new Question(
                        "Which device is responsible for forwarding packets between different networks?",
                        List.of("Hub", "Switch", "Router", "Bridge")
                ),
                new Question(
                        "Which protocol translates domain names into IP addresses?",
                        List.of("DNS", "DHCP", "FTP", "SMTP")
                ),
                new Question(
                        "What does HTTPS use to secure HTTP traffic?",
                        List.of("SSL/TLS", "SSH", "IPsec", "TLS only")
                ),
                new Question(
                        "Which protocol automatically assigns IP addresses on a network?",
                        List.of("DNS", "DHCP", "ARP", "ICMP")
                ),
                new Question(
                        "Which OSI layer is responsible for error detection and frame synchronization?",
                        List.of("Physical", "Data Link", "Network", "Transport")
                ),
                new Question(
                        "What is the default subnet mask for a Class C IPv4 address?",
                        List.of("255.0.0.0", "255.255.0.0", "255.255.255.0", "255.255.255.255")
                ),
                new Question(
                        "MAC in MAC address stands for…?",
                        List.of("Media Access Control", "Multiple Address Code", "Message Acknowledgment Code", "Master Access Channel")
                ),
                new Question(
                        "Which protocol would you use to securely copy files over SSH?",
                        List.of("SCP", "FTP", "HTTP", "TFTP")
                )
        );

        model.addAttribute("challenge", chal);
        model.addAttribute("questions", questions);
        model.addAttribute("timeLimit", 1);  // minutes
        return "challenges/take";
    }


    // 3.3 (optional) handle Finish
    @PostMapping("/challenges/{id}/finish")
    public String finish(@PathVariable Long id,
                         @RequestParam Map<String,String> answers,
                         Model model)
    {
        // TODO: grade answers, show result
        return "redirect:/challenges";
    }
}
