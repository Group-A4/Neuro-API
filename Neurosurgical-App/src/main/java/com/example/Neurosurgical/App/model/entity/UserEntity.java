package com.example.Neurosurgical.App.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Entity
@Data
@NoArgsConstructor
@Table(name = "students")
public class UserEntity extends BaseEntity {
    @Column(name = "last_name")
    private String last_name ;


    @Column(name = "First_name")
    private String First_name;

//    @Column(name="password")
//    private String password;
//
//    @Column(name="email_faculta")
//    private String emailFac;
//
//    @Column(name="email_personal")
//    private String emailPersonal;


}
