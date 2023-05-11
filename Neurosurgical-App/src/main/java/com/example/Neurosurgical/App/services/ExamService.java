package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExamService {
    Optional<List<ExamQuestionDto>> findById(Long id) throws EntityNotFoundException;

    Optional<List<ExamDto>> findByCourseId(Long id) throws EntityNotFoundException;
    
    void deleteExamById(Long id) throws EntityNotFoundException;
}
