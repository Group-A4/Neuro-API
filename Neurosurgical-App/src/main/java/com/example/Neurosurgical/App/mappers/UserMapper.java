package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.UserDto;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(UserEntity user){

        return UserDto.builder()
                .emailFaculty(user.getEmailFaculty())
                .emailPersonal(user.getEmailPersonal())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build();
    }
}
