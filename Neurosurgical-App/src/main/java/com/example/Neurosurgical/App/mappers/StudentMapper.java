package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.model.entity.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public static StudentDto toDto(StudentEntity studentEntity){
        return StudentDto.builder()
                .code(studentEntity.getCode())
                .year(studentEntity.getYear())
                .semester(studentEntity.getSemester())
                .birthDate(studentEntity.getBirthDate())
                .build();
    }
}
