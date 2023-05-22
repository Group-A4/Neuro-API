package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    @Query(value = "SELECT * FROM exam WHERE code = :codeExam", nativeQuery = true)
    Optional<ExamEntity> findByCode(@Param("codeExam") String code);

    @Query(value = "SELECT e.id, e.id_course, e.id_professor, e.title, e.code, e.date, e.time_exam, e.evaluation_type, e.points FROM exam e JOIN student_took_exams ste on e.id = ste.id_exam WHERE id_student=:idStudent", nativeQuery = true)
    List<ExamEntity> findByIdStudent(@Param("idStudent") Long idStudent);

    @Query(value = "SELECT e.id, e.id_course, e.id_professor, e.title, e.code, e.date, e.time_exam, e.evaluation_type, e.points FROM exam e WHERE e.id_course = :idCourse", nativeQuery = true)
    List<ExamEntity> findByIdCourse(@Param("idCourse") Long idCourse);
}
