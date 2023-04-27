package com.example.Neurosurgical.App.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "exam_has_questions")
public class ExamHasQuestionsEntity extends BaseEntity{
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_exam")
    private CourseEntity course;
}
