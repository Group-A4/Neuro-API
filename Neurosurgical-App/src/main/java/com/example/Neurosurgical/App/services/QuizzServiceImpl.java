package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.QuestionQuizzMapper;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import com.example.Neurosurgical.App.repositories.QuestionQuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizzServiceImpl implements QuizzService {

    final private QuestionQuizzRepository questionQuizzRepository;
    final private int NR_QUESTIONS_FOR_QUIZZ = 5;

    @Autowired
    public QuizzServiceImpl(QuestionQuizzRepository questionQuizzRepository) {
        this.questionQuizzRepository = questionQuizzRepository;
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByCourseId(Long id) throws EntityNotFoundException {

        List<QuestionQuizzDto> listQuestions = new ArrayList<>();

        Long nrQuestions = this.questionQuizzRepository.countQuestionsWithCourseId(id);
        if ( nrQuestions == 0){
            throw new EntityNotFoundException("Question", id);
        }

        for( int i=0 ; i < NR_QUESTIONS_FOR_QUIZZ ; ++ i) {
            int randomQuestion = (int) (Math.random() * nrQuestions) + 1;
            Optional <QuestionQuizzEntity> questionQuizzEntity = this.questionQuizzRepository.findByCourseId(id, randomQuestion);

            if(questionQuizzEntity.isEmpty()){
                throw new EntityNotFoundException("Question", id);
            }

            listQuestions.add(QuestionQuizzMapper.toDto(questionQuizzEntity.get()));
        }

        return Optional.of(listQuestions);

    }
}