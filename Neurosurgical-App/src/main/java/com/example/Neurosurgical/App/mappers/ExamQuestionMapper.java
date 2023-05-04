package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamQuestionMapper {
    public static ExamQuestionDto toDto (ExamQuestionEntity examQuestionEntity,
                                         List<ExamAnswerEntity> examAnswersEntity,
                                         List<CorrectExamAnswerEntity> correctExamAnswersEntity) {
        return ExamQuestionDto.builder().questionText(examQuestionEntity.getQuestionText())
                .idCourse(examQuestionEntity.getCourse().getId())
                .idProfessor(examQuestionEntity.getProfessor().getIdUser())
                .answersQuestions(examAnswersEntity.stream().map( answer ->
                        ExamAnswerMapper.toDto(answer, correctExamAnswersEntity))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
