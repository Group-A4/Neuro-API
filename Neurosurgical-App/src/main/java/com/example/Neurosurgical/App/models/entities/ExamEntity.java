package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "exam")
public class ExamEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @Column(name = "time_exam")
    private Integer timeExam;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity professor;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<QuestionExamEntity> questionsExam;

    public ExamEntity() {
        this.questionsExam = new ArrayList<>();
    }
}
