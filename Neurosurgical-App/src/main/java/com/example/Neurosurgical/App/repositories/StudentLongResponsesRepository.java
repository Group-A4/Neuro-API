package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.StudentLongResponsesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentLongResponsesRepository extends JpaRepository<StudentLongResponsesEntity, Long> {
    @Query(value = "SELECT * FROM student_long_responses WHERE id_student_question_points = :id1", nativeQuery = true)
    List<StudentLongResponsesEntity> findByIdStudentQuestionPoints(@Param("id1") long idStudentQuestionPoints);
}
