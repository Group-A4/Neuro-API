package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;
import javax.persistence.Column;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="password")
    private String password;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="email_faculty")
    private String emailFaculty;

    @Column(name="email_personal")
    private String emailPersonal;

    @Column(name="role")
    private Integer role;

}