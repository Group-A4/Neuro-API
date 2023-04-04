package com.example.Neurosurgical.App.model.entity;


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
//    @Column(name = "last_name")
//    private String last_name ;
//
//
//    @Column(name = "First_name")
//    private String First_name;

    @Column(name="password")
    private String password;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name="email_faculta")
    private String emailFac;

    @Column(name="email_personal")
    private String emailPersonal;


}
