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
public class ExamStudentSummariseDto {
    private Long idStudent;
    private String code;
    private Double pointsStudent;
    private Double pointsExam;
}
