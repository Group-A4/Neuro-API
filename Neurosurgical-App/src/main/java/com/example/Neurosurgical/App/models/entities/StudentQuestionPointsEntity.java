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
@Table(name = "student_question_points")
public class StudentQuestionPointsEntity extends BaseEntity {

    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "id_question")
    private Long idQuestion;

    @Column(name = "points_given")
    private Double pointsGiven;

    @JsonIgnore
    @OneToMany(mappedBy = "studentQuestionPoints", cascade = CascadeType.ALL)
    private List<StudentLongResponsesEntity> studentLongResponses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentQuestionPoints")
    private List<StudentMultipleChoiceResponsesEntity> studentMultipleChoiceResponsesList;

    public StudentQuestionPointsEntity() {
        this.studentLongResponses = new ArrayList<>();
        this.studentMultipleChoiceResponsesList = new ArrayList<>();
    }
}
