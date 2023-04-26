package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.dtos.CorrectAnswerQuizzDto;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectAnswerQuizzMapper {
    public static CorrectAnswerQuizzDto toDto(CorrectAnswerQuizzEntity correctAnswerQuizzEntity){
        return CorrectAnswerQuizzDto.builder()
                .idQuestion(correctAnswerQuizzEntity.getQuestion().getId())
                .idAnswer(correctAnswerQuizzEntity.getAnswer().getId())
                .build();
    }

    public static CorrectAnswerQuizzEntity fromDto(AnswerQuizzDto answerQuizzDto, QuestionQuizzEntity questionQuizzEntity){
        return CorrectAnswerQuizzEntity.builder()
                .answer(AnswerQuizzMapper.fromDto(answerQuizzDto, questionQuizzEntity))
                .question(questionQuizzEntity)
                .build();
    }
}
