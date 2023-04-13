package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.StudentDto;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public static StudentDto toDto(UserEntity userEntity, StudentEntity studentEntity){
        return StudentDto.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .emailFaculty(userEntity.getEmailFaculty())
                .emailPersonal(userEntity.getEmailPersonal())
                .code(studentEntity.getCode())
                .year(studentEntity.getYear())
                .semester(studentEntity.getSemester())
                .birthDate(studentEntity.getBirthDate())
                .build();
    }

    public static StudentEntity fromDto(StudentDto studentDto){
        return StudentEntity.builder()
                .code(studentDto.getCode())
                .year(studentDto.getYear())
                .semester(studentDto.getSemester())
                .birthDate(studentDto.getBirthDate())
                .build();
    }
}
