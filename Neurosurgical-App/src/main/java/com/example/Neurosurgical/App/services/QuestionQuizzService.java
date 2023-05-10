package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionQuizzService {
    Optional<QuestionQuizzDto> findById(Long id) throws EntityNotFoundException;

    List<QuestionQuizzDto> findAll();

    Optional<List<QuestionQuizzDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException;

    Optional<List<QuestionQuizzDto>> findByIdProfessor(Long id) throws EntityNotFoundException;

    Optional<List<QuestionQuizzDto>> findByIdCourse(Long id) throws EntityNotFoundException ;

    Optional<List<QuestionQuizzDto>> findByIdCourseAndLectureNumber(Long idCourse, Integer lectureNumber) throws EntityNotFoundException;

    Optional<List<Integer>> getLecturesByIdCourse(Long idCourse) throws EntityNotFoundException;

    void createQuestionQuizz(QuestionQuizzDto questionQuizzDto) throws EntityNotFoundException;

    void deleteQuestionQuizzById(Long id) throws EntityNotFoundException;

    void updateQuestionQuizz(Long id, QuestionQuizzDto questionQuizzDto) throws EntityNotFoundException;

    void updateQuestionQuizzText(Long id, String questionText) throws EntityNotFoundException;

}
