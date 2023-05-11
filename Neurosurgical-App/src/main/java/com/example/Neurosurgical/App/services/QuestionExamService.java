package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionExamService {
    Optional<QuestionExamDto> findById(Long id) throws EntityNotFoundException;

    List<QuestionExamDto> findAll();

    void createQuestionExam(QuestionExamDto questionExamDto) throws EntityNotFoundException;

    void deleteQuestionExamById(Long id) throws EntityNotFoundException;
}
