package com.example.Neurosurgical.App.models.dtos;

import jakarta.persistence.Column;
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
public class CourseDto {
    private String code;
    private Long id;

    private String title;

    private Integer year;

    private Integer semester;

    private Integer credits;
}
