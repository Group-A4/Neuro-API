package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
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

    public static QuestionQuizzDto toDto (QuestionQuizzEntity questionQuizzEntity,
                                          List<AnswerQuizzEntity> answersQuizzEntity,
                                          List<CorrectAnswerQuizzEntity> correctAnswersQuizzEntity){

        return QuestionQuizzDto.builder()
                .id(questionQuizzEntity.getId())
                .questionText(questionQuizzEntity.getQuestionText())
                .timeMinutes(questionQuizzEntity.getTimeMinutes())
                .difficulty(questionQuizzEntity.getDifficulty())
                .lectureNumber(questionQuizzEntity.getLectureNumber())
                .idCourse(questionQuizzEntity.getCourse().getId())
                .idProfessor(questionQuizzEntity.getProfessor().getIdUser())
                .answersQuestion(answersQuizzEntity.stream().map( answer -> //foreach Answer to this question, we map it to a AnswerQuizzDto, where in the second argument we pass the list of correct answers to this question
                                AnswerQuizzMapper.toDto(answer, correctAnswersQuizzEntity))
                                .collect(Collectors.toList())
                        )
                .build();
    }

    public static QuestionQuizzEntity fromDto(QuestionQuizzDto questionQuizzDto) {

            final List<AnswerQuizzDto> answersQuizzDtoList = questionQuizzDto.getAnswersQuestion();

            final List<AnswerQuizzEntity> answersQuizzEntityList = new ArrayList<>();

            final List<CorrectAnswerQuizzEntity> correctAnswersQuizzEntityList = new ArrayList<>();

            QuestionQuizzEntity questionQuizzEntity =
                    QuestionQuizzEntity.builder()
                    .questionText(questionQuizzDto.getQuestionText())
                    .lectureNumber(questionQuizzDto.getLectureNumber())
                    .correctAnswersQuestion(correctAnswersQuizzEntityList)
                    .answersQuestion(answersQuizzEntityList)
                    .build();
            if(questionQuizzDto.getTimeMinutes() != null){
                questionQuizzEntity.setTimeMinutes(questionQuizzDto.getTimeMinutes());
            }
            if(questionQuizzDto.getDifficulty() != null){
                questionQuizzEntity.setDifficulty(questionQuizzDto.getDifficulty());
            }

            answersQuizzDtoList.forEach(answer -> {
                AnswerQuizzEntity answerQuizzEntity = AnswerQuizzMapper.fromDto(answer, questionQuizzEntity);
                answersQuizzEntityList.add(answerQuizzEntity);
                if(answer.isCorrect())
                    correctAnswersQuizzEntityList.add(CorrectAnswerQuizzMapper.fromAnswerQuizzEntity(answerQuizzEntity, questionQuizzEntity));
            });

            return questionQuizzEntity;
    }



}
