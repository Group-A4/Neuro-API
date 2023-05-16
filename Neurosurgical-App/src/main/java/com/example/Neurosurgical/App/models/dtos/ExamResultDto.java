package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultDto {
    private Long id;
    private Long idCourse;
    private Long idProfessor;
    private String title;
    private Timestamp date;
    private Integer timeExam;
    private Integer evaluationType;
    private Double totalPoints;

    private List<QuestionMultipleChoiceExamResultDto> questionsMultipleChoiceResult;

    private List<QuestionLongResponseExamResultDto> questionsLongResponseResult;
}
