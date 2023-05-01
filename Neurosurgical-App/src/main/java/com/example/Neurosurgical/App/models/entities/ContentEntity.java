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
    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContentType type;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity professor;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<MarkdownContentEntity> markdownContents;
}
