package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.MaterialsMarkdownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsMarkdownRepository extends JpaRepository<MaterialsMarkdownEntity,Long> {
    @Query(value = "SELECT * FROM materials_markdown WHERE id_material = :material_id",nativeQuery = true)
    MaterialsMarkdownEntity findByMaterialId(@Param("material_id") Long materialId);
}
