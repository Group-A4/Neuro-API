package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.StudentQuestionPointsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentQuestionPointsRepository extends JpaRepository<StudentQuestionPointsEntity, Long> {
    @Query(value = "SELECT * FROM student_question_points WHERE id_student = :idStudent AND id_question = :idQuestion", nativeQuery = true)
    Optional<StudentQuestionPointsEntity> findByIdStudentAndIdQuestion(@Param("idStudent") Long idStudent,@Param("idQuestion") Long idQuestion);
}
