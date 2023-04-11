package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.*;
import com.example.Neurosurgical.App.model.entity.Role;
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

    public static UserEntity fromDto(UserDto userDto){
        return UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .emailFaculty(userDto.getEmailFaculty())
                .emailPersonal(userDto.getEmailPersonal())
                .build();
    }

    public static UserDto fromProfessorDtoToUserDto(ProfessorDto professorDto){
        return UserDto.builder()
                .firstName(professorDto.getFirstName())
                .lastName(professorDto.getLastName())
                .emailFaculty(professorDto.getEmailFaculty())
                .emailPersonal(professorDto.getEmailPersonal())
                .build();
    }

    public static UserEntity fromProfessorCreationDtoToUserEntity(ProfessorCreationDto professorCreationDto){
        return UserEntity.builder()
                .firstName(professorCreationDto.getFirstName())
                .lastName(professorCreationDto.getLastName())
                .emailFaculty(professorCreationDto.getEmailFaculty())
                .emailPersonal(professorCreationDto.getEmailPersonal())
                .password(professorCreationDto.getPassword())
                .role(Role.PROFESSOR.ordinal())
                .build();
    }

    public static UserEntity fromStudentCreationDtoToUserEntity(StudentCreationDto studentCreationDto){
        return UserEntity.builder()
                .firstName(studentCreationDto.getFirstName())
                .lastName(studentCreationDto.getLastName())
                .emailFaculty(studentCreationDto.getEmailFaculty())
                .emailPersonal(studentCreationDto.getEmailPersonal())
                .password(studentCreationDto.getPassword())
                .role(Role.STUDENT.ordinal())
                .build();
    }

    public static UserDto fromStudentDtoToUserDto(StudentDto studentDto) {
        return UserDto.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .emailFaculty(studentDto.getEmailFaculty())
                .emailPersonal(studentDto.getEmailPersonal())
                .build();
    }
}
