package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "active_exam")
public class ActiveExamEntity {

    @Id
    @Column(name = "id_exam")
    private Long idExam;

}
