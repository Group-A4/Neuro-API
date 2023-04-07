package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserAdminDTO implements EntityDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String emailFaculty;
    private String emailPersonal;
    private String password;
    private int role;
    private Long id_users;
}
