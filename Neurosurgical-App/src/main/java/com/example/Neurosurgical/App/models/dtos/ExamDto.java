package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDto {
    private Long idCourse;
    private String title;
    //TODO: Add these
//    private String code;
//    private Integer time;
//    private List<ExamQuestionDto> questions;
}
