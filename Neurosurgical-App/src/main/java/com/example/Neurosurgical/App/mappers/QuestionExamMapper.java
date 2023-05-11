package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionExamMapper {
    public static QuestionExamDto toDto(QuestionExamEntity questionExamEntity,
                                        List<AnswerExamEntity> answersExamEntity,
                                        List<CorrectAnswerExamEntity> correctAnswersExamEntity){

        return QuestionExamDto.builder()
                .id(questionExamEntity.getId())
                .questionText(questionExamEntity.getQuestionText())
                .idExam(questionExamEntity.getExam().getId())
                .idCourse(questionExamEntity.getCourse().getId())
                .idProfessor(questionExamEntity.getProfessor().getIdUser())
                .answersQuestion(answersExamEntity.stream().map( answer -> //foreach Answer to this question, we map it to a AnswerExamDto, where in the second argument we pass the list of correct answers to this question
                                AnswerExamMapper.toDto(answer, correctAnswersExamEntity))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static QuestionExamEntity fromDto(QuestionExamDto questionExamDto) {

        final List<AnswerExamDto> answersExamDtoList = questionExamDto.getAnswersQuestion();

        final List<AnswerExamEntity> answersExamEntityList = new ArrayList<>();

        final List<CorrectAnswerExamEntity> correctAnswersExamEntityList = new ArrayList<>();

        QuestionExamEntity questionExamEntity =
                QuestionExamEntity.builder()
                        .points(questionExamDto.getPoints())
                        .questionText(questionExamDto.getQuestionText())
                        .correctAnswersQuestion(correctAnswersExamEntityList)
                        .answersQuestion(answersExamEntityList)
                        .build();

        answersExamDtoList.forEach(answer -> {
            AnswerExamEntity answerExamEntity = AnswerExamMapper.fromDto(answer, questionExamEntity);
            answersExamEntityList.add(answerExamEntity);
            if(answer.isCorrect())
                correctAnswersExamEntityList.add(CorrectAnswerExamMapper.fromAnswerExamEntity(answerExamEntity, questionExamEntity));
        });

        return questionExamEntity;
    }
}
