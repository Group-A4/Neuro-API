package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content")
@Builder
public class ContentEntity extends BaseEntity{
    @Column(name="link")
    private String link;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private Integer type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity professor;

    @JsonIgnore
    @ManyToMany(mappedBy = "contents")
    private List<MaterialsMarkdownEntity> markdownEntityList;
}
