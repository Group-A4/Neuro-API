package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.repositories.AnswerExamRepository;
import com.example.Neurosurgical.App.repositories.CorrectAnswerExamRepository;
import com.example.Neurosurgical.App.repositories.QuestionExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionExamServiceIml implements QuestionExamService{
    final private QuestionExamRepository questionExamRepository;
    final private AnswerExamRepository answerExamRepository;
    final private CorrectAnswerExamRepository correctAnswerExamRepository;

    @Autowired
    public QuestionExamServiceIml(QuestionExamRepository questionExamRepository,
                                  AnswerExamRepository answerExamRepository,
                                  CorrectAnswerExamRepository correctAnswerExamRepository) {

        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.correctAnswerExamRepository = correctAnswerExamRepository;
    }

    @Override
    public Optional<QuestionExamDto> findById(Long id) throws EntityNotFoundException {
        return Optional.empty();
    }

    @Override
    public List<QuestionExamDto> findAll() {
        return null;
    }

    @Override
    public void createQuestionExam(QuestionExamDto questionExamDto) throws EntityNotFoundException {

    }

    @Override
    public void deleteQuestionExamById(Long id) throws EntityNotFoundException {

    }
}
