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

    void createQuestionExam(QuestionExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException;

    void updateQuestionExam(QuestionExamDto questionExamCreationDto, Long idQuestion) throws EntityNotFoundException;

    void deleteQuestionExam(Long idQuestion) throws EntityNotFoundException;
}
