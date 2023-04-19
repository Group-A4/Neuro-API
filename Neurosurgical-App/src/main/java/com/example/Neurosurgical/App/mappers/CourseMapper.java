package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
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
                .title(courseDto.getTitle())
                .credits(courseDto.getCredits())
                .semester(courseDto.getSemester())
                .year(courseDto.getYear())
                .build();
    }

    public static CourseCreationDto toCreationDto(CourseEntity courseEntity){
        return CourseCreationDto.builder()
                .code(courseEntity.getCode())
                .title(courseEntity.getTitle())
                .credits(courseEntity.getCredits())
                .semester(courseEntity.getSemester())
                .year(courseEntity.getYear())
                .build();
    }

    public static CourseEntity fromCreationDto(CourseCreationDto courseCreationDto){
        return CourseEntity.builder()
                .code(courseCreationDto.getCode())
                .title(courseCreationDto.getTitle())
                .credits(courseCreationDto.getCredits())
                .semester(courseCreationDto.getSemester())
                .year(courseCreationDto.getYear())
                .build();
    }
}
