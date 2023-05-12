package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    @Query(value = "SELECT id FROM exam WHERE id_course = :idCourse",nativeQuery = true)
    Optional<ArrayList<Long>> findByIdCourse(@Param("idCourse") Long id);

    @Query(value = "SELECT * FROM exam WHERE id_professor = :idProfessor", nativeQuery = true)
    Optional<ArrayList<ExamEntity>> findByIdProfessor(@Param("idProfessor") Long id);

    @Query(value = "SELECT * FROM exam WHERE code = :codeP", nativeQuery = true)
    Optional<ExamEntity> findByCode(@Param("codeP") String code);

    @Query(value = "SELECT * FROM exam WHERE id = :idExam", nativeQuery = true)
    Optional<ExamEntity> findById(@Param("idExam") Long id);
}
