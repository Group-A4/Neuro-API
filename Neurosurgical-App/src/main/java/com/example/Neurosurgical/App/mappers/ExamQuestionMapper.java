package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.ExamAnswerDto;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public static ExamQuestionEntity fromDto(ExamQuestionDto examQuestionDto) {
        final List<ExamAnswerDto> examAnswersDtoList = examQuestionDto.getAnswersQuestions();
        final List<ExamAnswerEntity> examAnswersEntityList = new ArrayList<>();
        final List<CorrectExamAnswerEntity> correctExamAnswersEntityList = new ArrayList<>();

        ExamQuestionEntity examQuestionEntity = ExamQuestionEntity.builder()
                .questionText(examQuestionDto.getQuestionText())
                .correctAnswersQuestion(correctExamAnswersEntityList)
                .answersQuestion(examAnswersEntityList)
                .build();

        examAnswersDtoList.forEach( answer -> {
            ExamAnswerEntity examAnswerEntity = ExamAnswerMapper.fromDto(answer, examQuestionEntity);
            examAnswersEntityList.add(examAnswerEntity);
            if(answer.isCorrect()){
                correctExamAnswersEntityList.add(CorrectExamAnswerMapper.fromExamAnswerEntity(examAnswerEntity, examQuestionEntity));
            }
        });

        return examQuestionEntity;
    }
}
