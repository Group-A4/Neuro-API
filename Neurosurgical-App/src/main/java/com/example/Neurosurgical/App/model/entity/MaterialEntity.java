package com.example.Neurosurgical.App.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials")
@Builder
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_course")
    private Long id_course;
    @Column(name = "id_professor")
    private Long id_professor;
    @Column(name = "title")
    private String title;
    @Column(name = "link")
    private String link;
}