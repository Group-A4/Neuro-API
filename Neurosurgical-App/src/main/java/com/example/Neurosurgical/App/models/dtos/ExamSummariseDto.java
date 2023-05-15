package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSummariseDto {
    private Long id;
    private Long idCourse;
    private Long idProfessor;
    private String code;
    private String title;
    private Timestamp date;
    private Integer timeExam;
    private Integer evaluationType;
}
