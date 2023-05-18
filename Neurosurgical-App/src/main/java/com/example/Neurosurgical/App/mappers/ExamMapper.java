package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.*;
import com.example.Neurosurgical.App.models.entities.ExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExamMapper {
    public static ExamEntity fromCreationDto(ExamCreationDto examCreationDto){
        return ExamEntity.builder()
                .title(examCreationDto.getTitle())
                .date(examCreationDto.getDate())
                .timeExam(examCreationDto.getTimeExam())
                .evaluationType(examCreationDto.getEvaluationType()>=0 && examCreationDto.getEvaluationType()<=2? examCreationDto.getEvaluationType() : 1 )
//                .questionsExam( Stream.concat(
//                        examCreationDto.getQuestionsMultipleChoice().stream().map(QuestionExamMapper::fromCreationDto)
//                        ,examCreationDto.getQuestionsLongResponse().stream().map(QuestionExamMapper::fromLongResponseDto)   )
//                        .collect(Collectors.toList())   )
                .build();

    }

    public static ExamDto toDto(ExamEntity examEntity, boolean hideAnswers) {

        List<QuestionMultipleChoiceExamDto> questionsMultipleChoice = new ArrayList<>();
        List<QuestionLongResponseExamDto> questionsLongResponse = new ArrayList<>();

        for(QuestionExamEntity questionExamEntity : examEntity.getQuestionsExam()){
            if(questionExamEntity.getQuestionLongResponseExam() == null){
                questionsMultipleChoice.add(QuestionExamMapper.toDtoForExam(questionExamEntity, hideAnswers));
            }
            else{
                QuestionLongResponseExamDto questionLongResponseExamDto = QuestionExamMapper.toLongResponseDto(questionExamEntity);
                if(hideAnswers)
                    questionLongResponseExamDto.setExpectedResponse("");
                else
                    questionLongResponseExamDto.setExpectedResponse(questionExamEntity.getQuestionLongResponseExam().getExpectedResponse());
                questionsLongResponse.add(questionLongResponseExamDto);
            }
        }

        Collections.shuffle(questionsMultipleChoice);
        Collections.shuffle(questionsLongResponse);

        return ExamDto.builder()
                .id(examEntity.getId())
                .idProfessor(examEntity.getProfessor().getIdUser())
                .idCourse(examEntity.getCourse().getId())
                .title(examEntity.getTitle())
                .date(examEntity.getDate())
                .timeExam(examEntity.getTimeExam())
                .evaluationType(examEntity.getEvaluationType())
                .questionsMultipleChoice(questionsMultipleChoice)
                .questionsLongResponse(questionsLongResponse)
                .build();
    }

    public static ExamSummariseDto toSummariseDto(ExamEntity examEntity) {

        return ExamSummariseDto.builder()
                .id(examEntity.getId())
                .idCourse(examEntity.getCourse().getId())
                .idProfessor(examEntity.getProfessor().getIdUser())
                .code(examEntity.getCode())
                .title(examEntity.getTitle())
                .date(examEntity.getDate())
                .timeExam(examEntity.getTimeExam())
                .evaluationType(examEntity.getEvaluationType())
                .build();

    }
}
