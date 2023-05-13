package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionExamMapper {
    public static QuestionExamDto toDto(QuestionExamEntity questionExamEntity){

        return QuestionExamDto.builder()
                .id(questionExamEntity.getId())
                .points(questionExamEntity.getPoints())
                .questionText(questionExamEntity.getQuestionText())
                .idExam(questionExamEntity.getExam().getId())
                .idCourse(questionExamEntity.getCourse().getId())
                .idProfessor(questionExamEntity.getProfessor().getIdUser())
                .answersQuestion(
                        questionExamEntity.getAnswersQuestion().stream().map(AnswerExamMapper::toDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static QuestionExamEntity fromCreationDto(QuestionExamCreationDto questionExamDto) {

        final List<AnswerExamCreationDto> answersExamDtoList = questionExamDto.getAnswersQuestion();

        final List<AnswerExamEntity> answersExamEntityList = new ArrayList<>();

        QuestionExamEntity questionExamEntity =
                QuestionExamEntity.builder()
                        .points(questionExamDto.getPoints())
                        .questionText(questionExamDto.getQuestionText())
                        .answersQuestion(answersExamEntityList)
                        .build();

        answersExamDtoList.forEach(answer -> {
            AnswerExamEntity answerExamEntity = AnswerExamMapper.fromCreationDto(answer, questionExamEntity);
            if(answer.isCorrect()){
                answerExamEntity.setCorrectAnswerExam(CorrectAnswerExamMapper.fromAnswerExamEntity(answerExamEntity));
            }
            answersExamEntityList.add(answerExamEntity);
        });

        return questionExamEntity;
    }

}
