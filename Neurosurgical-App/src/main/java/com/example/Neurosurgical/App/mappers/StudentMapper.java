package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.model.entity.StudentEntity;


public class StudentMapper {
    public static StudentDto toDto(StudentEntity studentEntity){



        return StudentDto.builder()
                .firstName()
                .build();
    }
}
