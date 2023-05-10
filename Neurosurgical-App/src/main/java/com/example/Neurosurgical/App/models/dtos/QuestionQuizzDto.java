package com.example.Neurosurgical.App.models.dtos;

import jakarta.persistence.Column;
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
public class QuestionQuizzDto {

    private Long id;
    private String questionText;
    private Integer difficulty;
    private Double timeMinutes;
    private Integer lectureNumber;
    private Long idCourse;
    private Long idProfessor;
    private List<AnswerQuizzDto> answersQuestion;
}
