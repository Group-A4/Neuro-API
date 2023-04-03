package com.example.Neurosurgical.App.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {


    @Column(name="password")
    private String password;

    @Column(name="email_faculta")
    private String emailFac;

    @Column(name="email_personal")
    private String emailPersonal;


}
