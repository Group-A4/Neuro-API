package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials_markdown")
@Builder
public class MaterialsMarkdownEntity extends BaseEntity{
    @Column(name="markdown_text")
    Byte[] markdownText;

    @Column(name="html")
    Byte[] html;

    @ManyToMany(mappedBy = "materialsMarkdown")
    private List<MaterialEntity> materials;
}
