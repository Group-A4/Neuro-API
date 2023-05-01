package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.MarkdownContentEntity;
import com.example.Neurosurgical.App.models.entities.MaterialsMarkdownEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkdownContentRepository {

    @Query(value = "SELECT * FROM markdown_content WHERE id_material_markdown = :markdownId",nativeQuery = true)
    List<MarkdownContentEntity> findByMarkdownId(@Param("markdownId") Long markdownId);

    @Query(value = "SELECT * FROM markdown_content WHERE id_content = :contentId",nativeQuery = true)
    List<MarkdownContentEntity> findByContentId(@Param("contentId") Long contentId);
}
