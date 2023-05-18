package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.OneToOne;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials_markdown")
@Builder
public class MaterialsMarkdownEntity extends BaseEntity{
    @Column(name = "markdown_text", columnDefinition = "TEXT")
    private String markdownText;

    @Column(name = "html", columnDefinition = "TEXT")
    private String html;

    @OneToMany(mappedBy = "materialMarkdown")
    private List<MaterialEntity> materials;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialMarkdown")
    private List<MarkdownContentEntity> markdownContents;
}
