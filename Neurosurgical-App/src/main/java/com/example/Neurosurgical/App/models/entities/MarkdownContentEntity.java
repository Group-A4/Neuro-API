package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "markdown_content")
public class MarkdownContentEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_material_markdown", nullable = false)
    private MaterialsMarkdownEntity materialMarkdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_content", nullable = false)
    private ContentEntity content;
}
