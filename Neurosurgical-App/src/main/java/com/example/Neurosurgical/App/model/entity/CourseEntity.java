package com.example.Neurosurgical.App.model.entity;

import com.example.Neurosurgical.App.model.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "courses")
@Builder
public class CourseEntity extends BaseEntity {
    @Column(name="code", unique = true)
    private String code;

    @Column(name="title")
    private String title;

    @Column(name="year")
    private Integer year;

    @Column(name="semester")
    private Integer semester;

    @Column(name="credits")
    private Integer credits;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<StudentFollowsCoursesEntity> registrations;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<MaterialEntity> materials;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<DidacticEntity> teachings;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<QuizzEntity> quizzes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<QuestionQuizzEntity> questionsQuizzes;

    public CourseEntity(){
        this.registrations = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.teachings = new ArrayList<>();

        this.quizzes = new ArrayList<>();
        this.questionsQuizzes = new ArrayList<>();
    }
}