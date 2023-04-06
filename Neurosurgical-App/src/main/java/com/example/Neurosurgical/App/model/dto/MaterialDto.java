package com.example.Neurosurgical.App.model.dto;

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
public class MaterialDto {
    private Integer idCourse;

    private Integer idProfessor;

    private String title;

    private String link;
}
