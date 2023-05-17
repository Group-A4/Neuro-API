package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ActiveExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveExamRepository extends JpaRepository<ActiveExamEntity, Long> {
    @Query(value = "SELECT * FROM active_exams WHERE id_exam = id_exam", nativeQuery = true)
    List<ActiveExamEntity> findbyId(@Param("id_exam") Long idExam);
}
