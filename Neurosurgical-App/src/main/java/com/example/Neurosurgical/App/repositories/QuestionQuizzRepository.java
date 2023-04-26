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

    @Query(value = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER (ORDER BY id_course ) AS row_num FROM questions_quizz Where id_course = :courseId ) AS subquery WHERE row_num =:rowNr",nativeQuery = true)
    Optional<QuestionQuizzEntity> findByCourseId(@Param("courseId") Long id, @Param("rowNr") int rowNr);

    @Query(value = "Select count('c') from questions_quizz WHERE id_course = :courseId",nativeQuery = true)
    Long countQuestionsWithCourseId(@Param("courseId") Long courseId);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_professor = :idProfessor AND id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdProfessorAndIdCourse(@Param("idProfessor") Long idProfessor, @Param("idCourse") Long idCourse);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_professor = :idProfessor",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdProfessor(@Param("idProfessor") Long id);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_course = :idCourse",nativeQuery = true)
    Optional<List<QuestionQuizzEntity>> findByIdCourse(@Param("idCourse") Long id);

    @Query(value = "SELECT * FROM questions_quizz WHERE id_course = :idCourse AND id_professor = :idUser AND question_text = :questionText",nativeQuery = true)
    Optional<Object> findByCourseIdProffesorIdQuestionText(@Param("idCourse") Long id, @Param("idUser") Long idUser, @Param("questionText") String questionText);
}
