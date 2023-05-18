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
public class ExamCreationDto {
    private Long idCourse;
    private Long idProfessor;
    private String title;
    private Integer timeExam;
    private Timestamp date;
    private Integer evaluationType;
    private List<QuestionMultipleChoiceExamCreationDto> questionsMultipleChoice;
    private List<QuestionLongResponseExamCreationDto> questionsLongResponse;
}
