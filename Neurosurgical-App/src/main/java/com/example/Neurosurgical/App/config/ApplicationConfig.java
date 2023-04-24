package com.example.Neurosurgical.App.config;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserEntity userEntity = repository.findByFacMail(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException("User not found");

            }
            return userEntity;
        };
    }
}
