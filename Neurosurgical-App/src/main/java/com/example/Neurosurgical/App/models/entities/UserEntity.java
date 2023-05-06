package com.example.Neurosurgical.App.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class UserEntity extends BaseEntity {

    @Column(name="password")
    private String password;

    @Column(name="last_name")
    @NotNull(message = "Lastname cannot be null")
    private String lastName;

    @Column(name="first_name")
    @NotNull(message = "Firstname cannot be null")
    private String firstName;

    @Column(name="email_faculty", unique = true)
    @Email(message = "Email should be valid")
    private String emailFaculty;

    @Column(name="email_personal", unique = true)
    @Email(message = "Email should be valid")
    private String emailPersonal;

    @Column(name="role")
    private Integer role;


}
