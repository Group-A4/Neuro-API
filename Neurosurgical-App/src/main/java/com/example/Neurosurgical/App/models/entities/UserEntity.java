package com.example.Neurosurgical.App.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="email_faculty", unique = true)
    private String emailFaculty;

    @Column(name="email_personal", unique = true)
    private String emailPersonal;

    @Column(name="role")
    private Integer role;
}
