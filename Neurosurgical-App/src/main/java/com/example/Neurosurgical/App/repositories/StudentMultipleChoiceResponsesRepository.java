package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.StudentMultipleChoiceResponsesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentMultipleChoiceResponsesRepository extends JpaRepository<StudentMultipleChoiceResponsesEntity, Long> {
    @Query(value = "SELECT * FROM student_multiple_choice_responses WHERE id_student_question_points = :id1 AND id_answer = :idAnswer", nativeQuery = true)
    Optional<StudentMultipleChoiceResponsesEntity> findByIdStudentQuestionPointsAndIdAnswer(@Param("id1") long idStudentQuestionPoints, @Param("idAnswer") Long idAnswer);
}
