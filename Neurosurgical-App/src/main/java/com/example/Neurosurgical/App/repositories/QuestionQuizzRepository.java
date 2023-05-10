package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionQuizzRepository extends JpaRepository<QuestionQuizzEntity,Long>{

    @Query(value = "Select count('c') from questions_quizz WHERE id_course = :courseId",nativeQuery = true)
    Long countQuestionsWithCourseId(@Param("courseId") Long courseId);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_professor = :idProfessor AND id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdProfessorAndIdCourse(@Param("idProfessor") Long idProfessor, @Param("idCourse") Long idCourse);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_professor = :idProfessor",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdProfessor(@Param("idProfessor") Long id);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdCourse(@Param("idCourse") Long id);
}
