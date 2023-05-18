package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExamRepository extends JpaRepository<QuestionExamEntity, Long>{
    @Query(value = "SELECT q.id, q.id_exam,q.id_professor, q.question_text, q.points FROM questions_exam q LEFT JOIN long_response_questions lrq on q.id = lrq.id_question WHERE lrq.id_question IS NULL", nativeQuery = true)
    List<QuestionExamEntity> findMultipleChoiceQuestions();

    @Query(value = "SELECT q.id, q.id_exam,q.id_professor, q.question_text, q.points FROM questions_exam q JOIN long_response_questions lrq on q.id = lrq.id_question", nativeQuery = true)
    List<QuestionExamEntity> findLongResponseQuestions();
}
