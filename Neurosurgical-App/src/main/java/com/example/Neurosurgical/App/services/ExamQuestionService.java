package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.ExamQuestionNotFoundException;
import com.example.Neurosurgical.App.model.dto.ExamQuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExamQuestionService {
    Optional<ExamQuestionDto> findById(Long id) throws ExamQuestionNotFoundException;

    List<ExamQuestionDto> findAll();

    Optional<List<ExamQuestionDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws ExamQuestionNotFoundException;

    Optional<List<ExamQuestionDto>> findByIdProfessor(Long id) throws ExamQuestionNotFoundException;

    Optional<List<ExamQuestionDto>> findByIdCourse(Long id) throws ExamQuestionNotFoundException;

    void createExamQuestion(ExamQuestionDto examQuestionDto) throws ExamQuestionNotFoundException;

    void updateExamQuestionText(Long id, String questionText) throws ExamQuestionNotFoundException;

    void updateExamQuestion(Long id, ExamQuestionDto examQuestionDto) throws ExamQuestionNotFoundException;

    void deleteExamQuestionById(Long id) throws ExamQuestionNotFoundException;
}
