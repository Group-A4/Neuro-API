package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerExamRepository extends JpaRepository<AnswerExamEntity, Long> {
    @Query(value="SELECT * FROM answers_exam WHERE id_question = :iq_question;", nativeQuery = true)
    List<AnswerExamEntity> findByIdQuestion(@Param("id_question") Long idQuestion);
}
