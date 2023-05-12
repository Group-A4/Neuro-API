package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.entities.ExamEntity;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {
    public static ExamDto toDto (ExamEntity examEntity){
        return ExamDto.builder()
                .idCourse(examEntity.getCourse().getId())
                .title(examEntity.getTitle())
                .build();
    }

//    public static ExamEntity fromDto(ExamDto examDto, CourseDto courseDto) {
//        return ExamEntity.builder()
//                .ExamTitle(examDto.getTitle())
//                .course(examDto.getIdCourse())
//                .build();
//    }
}
