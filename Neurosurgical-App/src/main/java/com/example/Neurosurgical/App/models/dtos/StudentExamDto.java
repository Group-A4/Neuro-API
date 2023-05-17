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
public class StudentExamDto {
    private Long idStudent;
    private Long idExam;
    private Double studentPoints;
}
