package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionExamService {
    Optional<QuestionExamDto> findById(Long id) throws EntityNotFoundException;

    List<QuestionExamDto> findAll();

    Optional<List<QuestionExamDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException;

    Optional<List<QuestionExamDto>> findByIdProfessor(Long id) throws EntityNotFoundException;

    Optional<List<QuestionExamDto>> findByIdCourse(Long id) throws EntityNotFoundException;

    void createExamQuestion(QuestionExamDto questionExamDto) throws EntityNotFoundException;

    void deleteExamQuestionById(Long id) throws EntityNotFoundException;

    void updateExamQuestionText(Long id, String questionText) throws EntityNotFoundException;

    void updateExamQuestion(Long id, QuestionExamDto questionExamDto) throws EntityNotFoundException;

    void createQuestionExam(QuestionExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException;

    void updateQuestionExam(QuestionExamDto questionExamCreationDto, Long idQuestion) throws EntityNotFoundException;

    void deleteQuestionExam(Long idQuestion) throws EntityNotFoundException;
}
