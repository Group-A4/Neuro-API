package com.example.Neurosurgical.App.models.entities;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;

import javax.persistence.EnumType;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@Access(AccessType.FIELD)
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "password")
    private String password;

    @Column(name = "last_name")
    @NotNull(message = "Lastname cannot be null")
    private String lastName;

    @Column(name = "first_name")
    @NotNull(message = "Firstname cannot be null")
    private String firstName;

    @Column(name = "email_faculty", unique = true)
    @Email(message = "Email should be valid")
    private String emailFaculty;

    @Column(name = "email_personal", unique = true)
    @Email(message = "Email should be valid")
    private String emailPersonal;

    @Column(name = "role")
    private Integer role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role roleEnum;
        roleEnum = Role.values()[role];
        return List.of(new SimpleGrantedAuthority(roleEnum.name()));
    }

    @Override
    public String getUsername() {
        return emailFaculty;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
