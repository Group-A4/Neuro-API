package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface ExamHasQuestionsRepository extends JpaRepository<ExamQuestionEntity, Long> {
    @Query(value = "SELECT id_question FROM exam_has_questions WHERE id_exam = :id", nativeQuery = true)
    Optional<ArrayList<Long>> findByExamId(@Param("ExamId") Long id);
}