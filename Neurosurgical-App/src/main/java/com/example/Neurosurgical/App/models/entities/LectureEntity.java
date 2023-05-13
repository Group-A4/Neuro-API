package com.example.Neurosurgical.App.models.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lectures")
@Builder
public class LectureEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_course")
    private CourseEntity course;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "lecture")
    private List<MaterialEntity> materials;
}
