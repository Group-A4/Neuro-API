package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.dtos.CorrectAnswerQuizzDto;
import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectAnswerQuizzMapper {

    public static CorrectAnswerQuizzEntity fromAnswerQuizzEntity(AnswerQuizzEntity answerQuizzEntity,
                                                                 QuestionQuizzEntity questionQuizzEntity){
        return CorrectAnswerQuizzEntity.builder()
                .answer(answerQuizzEntity)
                .question(questionQuizzEntity)
                .build();
    }
}
