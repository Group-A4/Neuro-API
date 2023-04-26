package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerQuizzRepository extends JpaRepository<AnswerQuizzEntity, Long> {

    @Query(value = "SELECT * FROM answers_quizz where id_question = :id_question",nativeQuery = true)
    List<AnswerQuizzEntity> findByIdQuestion (@Param("id_question") Long idQuestion);

}
