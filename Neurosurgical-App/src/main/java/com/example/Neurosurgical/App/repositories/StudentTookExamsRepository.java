package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.StudentTookExamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentTookExamsRepository extends JpaRepository<StudentTookExamsEntity, Long> {
    @Query(value = "SELECT * FROM student_took_exams WHERE id_student = :idStudent AND id_exam = :idExam", nativeQuery = true)
    Optional<StudentTookExamsEntity> findByIdStudentAndIdExam(@Param("idStudent") Long idStudent, @Param("idExam") Long idExam);
}
