package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public static CourseDto toDto(CourseEntity courseEntity){
        return CourseDto.builder()
                .title(courseEntity.getTitle())
                .credits(courseEntity.getCredits())
                .semester(courseEntity.getSemester())
                .year(courseEntity.getYear())
                .build();
    }

    public static CourseEntity fromDto(CourseDto courseDto){
        return CourseEntity.builder()
//                .code(courseDto.getCode())
                .title(courseDto.getTitle())
                .credits(courseDto.getCredits())
                .semester(courseDto.getSemester())
                .year(courseDto.getYear())
                .build();
    }
}
