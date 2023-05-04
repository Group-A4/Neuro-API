package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswerEntity, Long> {
    @Query(value = "SELECT * FROM answers_exam where id_question = :id_question",nativeQuery = true)
    List<ExamAnswerEntity> findByIdQuestion (@Param("id_question") Long idQuestion);
}
