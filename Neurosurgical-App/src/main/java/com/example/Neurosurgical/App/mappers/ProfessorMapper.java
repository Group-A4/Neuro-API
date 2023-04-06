package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.model.entity.ProfessorEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public static ProfessorDto toDto(ProfessorEntity professorEntity){
        return ProfessorDto.builder()
                .code(professorEntity.getCode())
                .degree(professorEntity.getDegree())
                .build();
    }
}
