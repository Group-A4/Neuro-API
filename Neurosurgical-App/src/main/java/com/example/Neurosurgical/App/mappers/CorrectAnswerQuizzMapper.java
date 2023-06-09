package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectAnswerQuizzMapper {

    public static CorrectAnswerQuizzEntity fromAnswerQuizzEntity(AnswerQuizzEntity answerQuizzEntity){
        return CorrectAnswerQuizzEntity.builder()
                .answer(answerQuizzEntity)
                .build();
    }
}
