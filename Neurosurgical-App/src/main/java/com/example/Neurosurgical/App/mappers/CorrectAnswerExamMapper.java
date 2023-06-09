package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectAnswerExamMapper {
    public static CorrectAnswerExamEntity fromAnswerExamEntity(AnswerExamEntity answerExamEntity){
        return CorrectAnswerExamEntity.builder()
                .answer(answerExamEntity)
                .build();
    }
}
