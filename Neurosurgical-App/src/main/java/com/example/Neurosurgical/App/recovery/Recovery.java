package com.example.Neurosurgical.App.recovery;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recovery_code")
@Builder
public class Recovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_faculty")
    private String emailFaculty;

    @Column(name = "secret_code")
    private String secretCode;

    @Column(name = "time_expiration")
    private LocalDateTime timeExpiration;
}
