package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.repositories.AnswerExamRepository;
import com.example.Neurosurgical.App.repositories.CorrectAnswerExamRepository;
import com.example.Neurosurgical.App.repositories.QuestionExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionExamServiceImpl implements QuestionExamService{
    final private QuestionExamRepository questionExamRepository;
    final private AnswerExamRepository answerExamRepository;
    final private CorrectAnswerExamRepository correctAnswerExamRepository;

    @Autowired
    public QuestionExamServiceImpl(QuestionExamRepository questionExamRepository,
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
        return questionExamRepository.findAll().stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())) )
                .collect(Collectors.toList());
    }

    @Override
    public void createQuestionExam(QuestionExamDto questionExamDto) throws EntityNotFoundException {

    }

    @Override
    public void deleteQuestionExamById(Long id) throws EntityNotFoundException {

    }
}
