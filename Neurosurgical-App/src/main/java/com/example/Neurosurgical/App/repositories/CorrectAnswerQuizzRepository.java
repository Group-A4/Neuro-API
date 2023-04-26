package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorrectAnswerQuizzRepository extends JpaRepository<CorrectAnswerQuizzEntity, Long> {

    @Query(value = "SELECT * FROM correct_answers_quizz where id_question = :id_question ;",nativeQuery = true)
    List<CorrectAnswerQuizzEntity> findByIdQuestion (@Param("id_question") Long idQuestion);


}
