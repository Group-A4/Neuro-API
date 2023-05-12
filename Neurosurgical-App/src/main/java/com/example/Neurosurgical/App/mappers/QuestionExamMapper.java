package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionExamMapper {
    public static QuestionExamDto toDto (QuestionExamEntity questionExamEntity,
                                         List<AnswerExamEntity> examAnswersEntity,
                                         List<CorrectAnswerExamEntity> correctExamAnswersEntity) {
        return QuestionExamDto.builder().questionText(questionExamEntity.getQuestionText())
                .idCourse(questionExamEntity.getCourse().getId())
                .idProfessor(questionExamEntity.getProfessor().getIdUser())
                .answersQuestions(examAnswersEntity.stream().map( answer ->
                        AnswerExamMapper.toDto(answer, correctExamAnswersEntity))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static QuestionExamEntity fromDto(QuestionExamDto questionExamDto) {
        final List<AnswerExamDto> examAnswersDtoList = questionExamDto.getAnswersQuestions();
        final List<AnswerExamEntity> examAnswersEntityList = new ArrayList<>();
        final List<CorrectAnswerExamEntity> correctExamAnswersEntityList = new ArrayList<>();

        QuestionExamEntity questionExamEntity = QuestionExamEntity.builder()
                .questionText(questionExamDto.getQuestionText())
                .correctAnswersQuestion(correctExamAnswersEntityList)
                .answersQuestion(examAnswersEntityList)
                .build();

        examAnswersDtoList.forEach( answer -> {
            AnswerExamEntity answerExamEntity = AnswerExamMapper.fromDto(answer, questionExamEntity);
            examAnswersEntityList.add(answerExamEntity);
            if(answer.isCorrect()){
                correctExamAnswersEntityList.add(CorrectAnswerExamMapper.fromExamAnswerEntity(answerExamEntity, questionExamEntity));
            }
        });

        return questionExamEntity;
    }
}
