package com.example.ecommerceservice.config;

import com.example.ecommerceservice.entity.User;
import com.example.ecommerceservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfig {
    @Autowired
    private UserRepository userRepository;
    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//                return Optional.empty();
//            }
//            User user = (User) authentication.getPrincipal();
//
//            Optional<User> optionalUser = userRepository.findById(user.getId());
//            if(optionalUser.isEmpty())
//                userRepository.save(user);

            return Optional.ofNullable(null);
        };
    }
}

