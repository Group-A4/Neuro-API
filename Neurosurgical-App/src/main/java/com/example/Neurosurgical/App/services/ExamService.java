package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.ExamNotFoundException;
import com.example.Neurosurgical.App.model.entity.ExamQuestionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExamService {
    Optional<List<ExamQuestionEntity>> findById(Long id) throws ExamNotFoundException;
}
