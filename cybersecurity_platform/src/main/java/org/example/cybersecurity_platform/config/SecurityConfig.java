package org.example.cybersecurity_platform.config;

import org.example.cybersecurity_platform.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/", "/login*", "/register", "/error", "/css/**", "/js/**", "/profile").permitAll()

                        // only ADMINs can hit /admin/**
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // BLOGGERs (and ADMIN) can create/edit/delete news
                        .requestMatchers("/news/create", "/news/edit/**", "/news/delete/**")
                        .hasAnyRole("BLOGGER","ADMIN")

                        // CHALLENGERs (and ADMIN) can manage challenges
                        .requestMatchers("/challenges/create", "/challenges/edit/**", "/challenges/delete/**")
                        .hasAnyRole("CHALLENGER","ADMIN")

                        // LEARNERs (and ADMIN) can buy courses & view purchase history
                        .requestMatchers("/courses/buy/**", "/purchases/**")
                        .hasAnyRole("LEARNER","ADMIN")

                        // everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
