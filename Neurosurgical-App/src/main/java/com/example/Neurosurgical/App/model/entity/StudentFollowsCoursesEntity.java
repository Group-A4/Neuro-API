package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_follows_courses")
@Builder
public class StudentFollowsCoursesEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_student")
    StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "id_course")
    com.example.Neurosurgical.App.models.entities.CourseEntity course;
}
