package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AnswerQuizzMapper {

    public static AnswerQuizzDto toDto (AnswerQuizzEntity answerQuizzEntity,
                                        List<CorrectAnswerQuizzEntity> correctAnswerQuizzEntity){

        return AnswerQuizzDto.builder()
                .id(answerQuizzEntity.getId())
                .idQuestion(answerQuizzEntity.getQuestion().getId())
                .answerText(answerQuizzEntity.getAnswerText())
                .isCorrect(correctAnswerQuizzEntity != null && correctAnswerQuizzEntity.stream().anyMatch(
                        correctAnswerQuizz -> Objects.equals(correctAnswerQuizz.getAnswer().getId(), answerQuizzEntity.getId())
                ))
                .build();
    }

    public static AnswerQuizzEntity fromDto(AnswerQuizzDto answer, QuestionQuizzEntity questionQuizzEntity) {
        return AnswerQuizzEntity.builder()
                .answerText(answer.getAnswerText())
                .question(questionQuizzEntity)
                .build();
    }
}
