package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "student_took_exams")
public class StudentTookExamsEntity extends BaseEntity {
        @Column(name = "id_student")
        private Long idStudent;

        @Column(name = "id_exam")
        private Long idExam;

        @Column(name = "points_per_exam")
        private Double pointsPerExam;

        public StudentTookExamsEntity() {
        }
}
