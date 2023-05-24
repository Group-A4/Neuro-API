package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExamSummariseUpdateDto {

    private Long id;
    private String title;
    private Timestamp date;
    private Integer timeExam;
    private Integer evaluationType;

}

