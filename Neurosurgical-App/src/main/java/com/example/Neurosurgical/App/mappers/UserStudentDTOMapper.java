package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserStudentDTOMapper  {
    public static UserStudentDTO mapToDTO(UserEntity userEntity, StudentEntity studentEntity) {
        return UserStudentDTO.builder()
                .id(userEntity.getId())
                .lastName(userEntity.getLastName())
                .firstName(userEntity.getFirstName())
                .emailFaculty(userEntity.getEmailFaculty())
                .emailPersonal(userEntity.getEmailPersonal())
                .role(userEntity.getRole())
                .code(studentEntity.getCode())
                .year(studentEntity.getYear())
                .semester(studentEntity.getSemester())
                .build();
    }
}
