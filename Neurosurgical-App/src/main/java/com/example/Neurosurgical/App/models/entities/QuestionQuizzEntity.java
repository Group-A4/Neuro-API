package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "questions_quizz")
public class QuestionQuizzEntity extends BaseEntity {

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "difficulty",nullable = false, columnDefinition = "INT default 5")
    private Integer difficulty;

    @Column(name = "time_in_minutes", nullable = false, columnDefinition = "DECIMAL(4,2) NOT NULL DEFAULT 1.00")
    private Double timeMinutes;

    @ManyToOne
    @JoinColumn(name = "id_lecture")
    private LectureEntity lecture;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorEntity professor;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerQuizzEntity> answersQuestion;

    public QuestionQuizzEntity(){
        this.answersQuestion = new ArrayList<>();
    }

}
