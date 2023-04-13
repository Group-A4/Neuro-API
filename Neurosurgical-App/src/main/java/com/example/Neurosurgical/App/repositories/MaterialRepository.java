package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity,Long> {
    @Query(value = "SELECT * FROM materials WHERE title = :title",nativeQuery = true)
    MaterialEntity findByTitle(@Param("title") String title);
}
