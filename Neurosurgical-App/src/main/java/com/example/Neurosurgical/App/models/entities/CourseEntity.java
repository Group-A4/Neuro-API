package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "courses")
@Builder
public class CourseEntity extends BaseEntity{
    @Column(name="code", unique = true)
    @NotNull(message = "Code cannot be null")
    private String code;

    @Column(name="title")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Column(name="year")
    @Min(value = 1, message = "Year should not be less than 1")
    @Max(value = 10, message = "Year should not be greater than 10")
    private Integer year;

    @Column(name="semester")
    @Min(value = 1, message = "Semester should not be less than 1")
    @Max(value = 2, message = "Semester should not be greater than 2")
    private Integer semester;

    @Column(name="credits")
    @Min(value = 1, message = "Credits should not be less than 1")
    @Max(value = 6, message = "Credits should not be greater than 6")
    private Integer credits;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<StudentFollowsCoursesEntity> registrations;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<LectureEntity> lectures;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<DidacticEntity> teachings;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<QuestionQuizzEntity> questionsQuizzes;

    public CourseEntity(){
        this.registrations = new ArrayList<>();
        this.lectures = new ArrayList<>();
        this.teachings = new ArrayList<>();

        this.questionsQuizzes = new ArrayList<>();
    }
}
