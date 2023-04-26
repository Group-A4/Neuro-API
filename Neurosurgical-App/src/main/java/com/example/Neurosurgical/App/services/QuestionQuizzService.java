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

    Optional<List<QuestionQuizzDto>> findByIdProfessor(Long id);

    Optional<List<QuestionQuizzDto>> findByIdCourse(Long id);

    void createQuestionQuizz(QuestionQuizzDto questionQuizzDto);

    void deleteQuestionQuizzById(Long id);

    void updateQuestionQuizz(Long id, QuestionQuizzDto questionQuizzDto);

    void updateQuestionQuizzText(Long id, String questionText);
}
