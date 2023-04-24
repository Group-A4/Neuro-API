package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionQuizzMapper {

    public static QuestionQuizzDto toDto (QuestionQuizzEntity questionQuizzEntity){

        final List<AnswerQuizzEntity> answersQuizzEntity = questionQuizzEntity.getAnswersQuestion();

        final List<CorrectAnswerQuizzEntity> correctAnswersQuizzEntity = questionQuizzEntity.getCorrectAnswersQuestion();


        return QuestionQuizzDto.builder()
                .questionText(questionQuizzEntity.getQuestionText())
                .idCourse(questionQuizzEntity.getCourse().getId())
                .idProfessor(questionQuizzEntity.getProfessor().getIdUser())
                .answersQuestion(answersQuizzEntity.stream().map( answer -> //foreach Answer to this question, we map it to a AnswerQuizzDto, where in the second argument we pass the list of correct answers to this question
                                AnswerQuizzMapper.toDto(answer, correctAnswersQuizzEntity))
                                .collect(Collectors.toList())
                        )
                .build();
    }

}
