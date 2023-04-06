package com.example.Neurosurgical.App.dao;

import com.example.Neurosurgical.App.model.entity.ProfessorEntity;
import com.example.Neurosurgical.App.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfessorDao extends JpaRepository<ProfessorEntity,Long> {
    @Query(value = "SELECT * FROM Professors WHERE code = :code",nativeQuery = true)
    ProfessorEntity findByCode(@Param("code") String code);
}
