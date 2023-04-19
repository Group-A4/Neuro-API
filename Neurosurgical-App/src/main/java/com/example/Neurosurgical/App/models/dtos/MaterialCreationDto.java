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
public class MaterialCreationDto {
    private Long idCourse;
    private Long idProfessor;
    private String title;
    private String link;
}
