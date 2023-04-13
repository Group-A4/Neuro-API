package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
@Builder
public class CourseEntity extends BaseEntity{
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

}
