package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionExamDto {
    private Long id;
    private Long idExam;
    private Long idCourse;
    private Long idProfessor;
    private String questionTest;
    private Integer points;
}