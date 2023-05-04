package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorrectExamAnswerRepository extends JpaRepository<CorrectExamAnswerEntity, Long> {
    @Query(value = "SELECT * FROM correct_answers_exam where id_question = :id_question ;",nativeQuery = true)
    List<CorrectExamAnswerEntity> findByIdQuestion (@Param("id_question") Long idQuestion);
}
