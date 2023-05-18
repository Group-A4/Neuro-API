package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public static ProfessorDto toDto(UserEntity userEntity, ProfessorEntity professorEntity){
        return ProfessorDto.builder()
                .id(userEntity.getId())
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
                .idUser(professorDto.getId())
                .code(professorDto.getCode())
                .degree(professorDto.getDegree())
                .build();
    }
}
