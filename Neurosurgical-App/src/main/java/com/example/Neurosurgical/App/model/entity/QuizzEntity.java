package com.example.Neurosurgical.App.model.entity;

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
@Table(name = "quizz")
public class QuizzEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private com.example.Neurosurgical.App.models.entities.CourseEntity course;

    @Column(name = "title")
    private String titleQuizz;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizz")
    private List<QuizzHasQuestionsEntity> quizzHasQuestions;

    public QuizzEntity(){

        this.quizzHasQuestions = new ArrayList<>();

    }

}