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
@Table(name = "exam")
public class ExamEntity extends BaseEntity{
    @Id
    Long id;

    @Column(name = "title")
    String title;

    @ManyToOne
    @JoinColumn(name = "id_curs")
    private CourseEntity course;
}
