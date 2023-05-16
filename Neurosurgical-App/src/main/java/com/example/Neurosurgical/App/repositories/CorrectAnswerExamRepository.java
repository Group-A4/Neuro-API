package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorrectAnswerExamRepository extends JpaRepository<CorrectAnswerExamEntity, Long> {
    @Query(value = "SELECT * FROM correct_answers_exam WHERE id_answer = :idAnswer", nativeQuery = true)
    Optional<CorrectAnswerExamEntity> findByIdAnswer(@Param("idAnswer") Long id);
}
