package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ExamQuestionMapper;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import com.example.Neurosurgical.App.repositories.CorrectExamAnswerRepository;
import com.example.Neurosurgical.App.repositories.ExamAnswerRepository;
import com.example.Neurosurgical.App.repositories.ExamHasQuestionsRepository;
import com.example.Neurosurgical.App.repositories.ExamQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    final private ExamQuestionRepository examQuestionRepository;
    final private ExamHasQuestionsRepository examHasQuestionsRepository;
    final private ExamAnswerRepository examAnswerRepository;
    final private CorrectExamAnswerRepository correctExamAnswerRepository;

    @Autowired
    public ExamServiceImpl(ExamQuestionRepository examQuestionRepository,
                           ExamHasQuestionsRepository examHasQuestionsRepository, ExamAnswerRepository examAnswerRepository,
                           CorrectExamAnswerRepository correctExamAnswerRepository) {
        this.examQuestionRepository = examQuestionRepository;
        this.examHasQuestionsRepository = examHasQuestionsRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.correctExamAnswerRepository = correctExamAnswerRepository;
    }

    @Override
    public Optional<List<ExamQuestionDto>> findById(Long id) throws EntityNotFoundException {
        //return the exam questions with the specified id
        List<ExamQuestionDto> exam = new ArrayList<>();

        Optional<ArrayList<Long>> questionsIds = this.examHasQuestionsRepository.findByExamId(id);
        if(questionsIds.isEmpty()){
            throw new EntityNotFoundException("Question", id);
        }

        int size = questionsIds.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            this.examQuestionRepository.findByQuestionId(questionsIds.get().get(i));
            Optional<ExamQuestionEntity> opt = this.examQuestionRepository.findByQuestionId(questionsIds.get().get(i));

            if(opt.isEmpty()){
                throw new EntityNotFoundException("Question", id);
            }

            exam.add(ExamQuestionMapper.toDto(
                    opt.get(),
                    this.examAnswerRepository.findByIdQuestion(opt.get().getId()),
                    this.correctExamAnswerRepository.findByIdQuestion(opt.get().getId())
                    )
            );
        }
        return Optional.of(exam);
    }
}
