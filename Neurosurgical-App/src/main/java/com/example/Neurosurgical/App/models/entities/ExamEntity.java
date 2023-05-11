package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
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

    @Column(name = "title")
    private String ExamTitle;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<ExamQuestionEntity> examQuestions;

    public ExamEntity(){
        this.examQuestions = new ArrayList<>();
    }
}
