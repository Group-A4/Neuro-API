package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExamQuestionService {
    Optional<ExamQuestionDto> findById(Long id) throws EntityNotFoundException;

    List<ExamQuestionDto> findAll();

    Optional<List<ExamQuestionDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException;

    Optional<List<ExamQuestionDto>> findByIdProfessor(Long id) throws EntityNotFoundException;

    Optional<List<ExamQuestionDto>> findByIdCourse(Long id) throws EntityNotFoundException;

    void createExamQuestion(ExamQuestionDto examQuestionDto) throws EntityNotFoundException;

    void updateExamQuestionText(Long id, String questionText) throws EntityNotFoundException;

    void updateExamQuestion(Long id, ExamQuestionDto examQuestionDto) throws EntityNotFoundException;

    void deleteExamQuestionById(Long id) throws EntityNotFoundException;
}
