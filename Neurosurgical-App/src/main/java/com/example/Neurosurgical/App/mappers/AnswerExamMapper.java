package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

@Component
public class AnswerExamMapper {

    public static AnswerExamDto toDto (AnswerExamEntity answerExamEntity){

        return AnswerExamDto.builder()
                .id(answerExamEntity.getId())
                .idQuestion(answerExamEntity.getQuestion().getId())
                .answerText(answerExamEntity.getAnswerText())
                .isCorrect(answerExamEntity.getCorrectAnswerExam() != null)
                .build();
    }

    public static AnswerExamDto toDtoForExam (AnswerExamEntity answerExamEntity, boolean hideAnswers){

        boolean isAnswerCorrect = answerExamEntity.getCorrectAnswerExam() != null;
        if(hideAnswers)
            isAnswerCorrect = false;

        return AnswerExamDto.builder()
                .id(answerExamEntity.getId())
                .idQuestion(answerExamEntity.getQuestion().getId())
                .answerText(answerExamEntity.getAnswerText())
                .isCorrect(isAnswerCorrect)
                .build();
    }

    public static AnswerExamEntity fromCreationDto(AnswerExamCreationDto answer, QuestionExamEntity questionExamEntity) {
        return AnswerExamEntity.builder()
                .answerText(answer.getAnswerText())
                .question(questionExamEntity)
                .build();
    }

    public static AnswerExamEntity fromDto(AnswerExamDto answer, QuestionExamEntity questionExamEntity) {
        return AnswerExamEntity.builder()
                .answerText(answer.getAnswerText())
                .question(questionExamEntity)
                .build();
    }

}
