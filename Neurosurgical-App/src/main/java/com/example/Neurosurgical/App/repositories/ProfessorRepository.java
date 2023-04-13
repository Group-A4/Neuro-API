package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {
    @Query(value = "SELECT * FROM Professors WHERE code = :code",nativeQuery = true)
    ProfessorEntity findByCode(@Param("code") String code);
}
