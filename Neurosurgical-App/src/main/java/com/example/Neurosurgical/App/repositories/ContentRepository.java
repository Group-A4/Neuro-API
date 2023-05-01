package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long>{

    @Query(value = "SELECT * FROM content WHERE id_professor = :id",nativeQuery = true)
    List<ContentEntity> findByProfessorId(@Param("id") Long id);

    @Query(value = "SELECT * FROM content WHERE  name = :name",nativeQuery = true)
    Optional<ContentEntity> findByName(@Param("name") String name);
}
