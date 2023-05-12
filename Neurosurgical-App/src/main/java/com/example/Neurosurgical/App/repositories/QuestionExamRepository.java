package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionExamRepository extends JpaRepository<QuestionExamEntity, Long> {
    @Query(value = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY id_course ) AS row_num FROM questions_exam Where id_course = :courseId ) AS subquery WHERE row_num =:rowNr",nativeQuery = true)
    Optional<QuestionExamEntity> findByCourseId(@Param("courseId") Long id, @Param("rowNr") int rowNr);

    @Query(value = "Select count('c') from questions_exam WHERE id_course = :courseId",nativeQuery = true)
    Long countQuestionsWithCourseId(@Param("courseId") Long courseId);

    @Query(value = "SELECT * FROM questions_exam WHERE id_professor = :idProfessor AND id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionExamEntity>> findByIdProfessorAndIdCourse(@Param("idProfessor") Long idProfessor, @Param("idCourse") Long idCourse);

    @Query(value = "SELECT * FROM questions_exam WHERE id_professor = :idProfessor",nativeQuery = true)
    Optional<List<QuestionExamEntity>> findByIdProfessor(@Param("idProfessor") Long id);

    @Query(value = "SELECT * FROM questions_exam WHERE id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionExamEntity>> findByIdCourse(@Param("idCourse") Long id);

    @Query(value = "SELECT * FROM questions_exam WHERE id = :id_question", nativeQuery = true)
    Optional<QuestionExamEntity> findByQuestionId(@Param("id_question") Long id);

    @Query(value = "SELECT id FROM questions_exam WHERE id_exam = :id", nativeQuery = true)
    Optional<ArrayList<Long>> findByExamId(@Param("id") Long id);
}
