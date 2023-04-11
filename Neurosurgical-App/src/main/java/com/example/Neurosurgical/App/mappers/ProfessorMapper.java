package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.ProfessorCreationDto;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.model.entity.ProfessorEntity;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public static ProfessorDto toDto(UserEntity userEntity, ProfessorEntity professorEntity){
        return ProfessorDto.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .emailFaculty(userEntity.getEmailFaculty())
                .emailPersonal(userEntity.getEmailPersonal())
                .code(professorEntity.getCode())
                .degree(professorEntity.getDegree())
                .build();
    }

    public static ProfessorEntity fromDto(ProfessorDto professorDto){
        return ProfessorEntity.builder()
                .code(professorDto.getCode())
                .degree(professorDto.getDegree())
                .build();
    }
}
