package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.ExamNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.QuestionNotFound;
import com.example.Neurosurgical.App.model.entity.ExamQuestionEntity;
import com.example.Neurosurgical.App.repository.CorrectExamAnswerRepository;
import com.example.Neurosurgical.App.repository.ExamAnswerRepository;
import com.example.Neurosurgical.App.repository.ExamHasQuestionsRepository;
import com.example.Neurosurgical.App.repository.ExamQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService{
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
    public Optional<List<ExamQuestionEntity>> findById(Long id) throws ExamNotFoundException{
        //return the exam questions with the specified id
        List<ExamQuestionEntity> exam = null;

        Optional<ArrayList<Long>> questionsIds = this.examHasQuestionsRepository.findByExamId(id);
        if(questionsIds.isEmpty()){
            throw new QuestionNotFound();
        }

        //aflam lungimea listei optionale prin metoda magica
        int size = questionsIds.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            this.examQuestionRepository.findByQuestionId(questionsIds.get().get(i));
            Optional<ExamQuestionEntity> opt = this.examQuestionRepository.findByQuestionId(questionsIds.get().get(i));

            if(opt.isPresent()){
                exam.add(opt.get());
            }
        }
        return Optional.of(exam);
    }
}
