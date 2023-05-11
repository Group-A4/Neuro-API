package com.example.Neurosurgical.App.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**")
                .permitAll()
                /*.requestMatchers("**").hasAuthority("ADMIN")
                .requestMatchers("/users/mail/**").hasAnyAuthority("ADMIN", "PROFESSOR", "STUDENT")
                .requestMatchers("/professors/course**").hasAnyAuthority("ADMIN", "PROFESSOR", "STUDENT")
                .requestMatchers("/professors/material**").hasAnyAuthority("ADMIN", "PROFESSOR", "STUDENT")
                .requestMatchers("/materials/**").hasAnyAuthority("ADMIN", "PROFESSOR")
                .requestMatchers(HttpMethod.GET, "/materials/**").hasAnyAuthority("ADMIN", "PROFESSOR", "STUDENT")
                .requestMatchers("/courses/**").hasAnyAuthority("ADMIN", "PROFESSOR")
                .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyAuthority("ADMIN", "PROFESSOR", "STUDENT")*/
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

