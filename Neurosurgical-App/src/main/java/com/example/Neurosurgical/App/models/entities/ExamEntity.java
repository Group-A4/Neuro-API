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
    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorEntity professor;

    @Column(name = "title")
    private String ExamTitle;

    @Column(name = "code")
    private String code;

    @Column(name = "time_exam")
    private Integer time;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<ExamQuestionEntity> examQuestions;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<StudentPointsEntity> studentPoints;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<LongAnswerResponsesEntity> longAnswerResponses;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<StudentTookExamsEntity> studentTookExams;
    
    public ExamEntity(){
        this.examQuestions = new ArrayList<>();
    }
}
