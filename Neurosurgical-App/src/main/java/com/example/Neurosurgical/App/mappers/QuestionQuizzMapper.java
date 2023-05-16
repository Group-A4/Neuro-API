package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerQuizzCreationDto;
import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.AnswerQuizzEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionQuizzMapper {

    public static QuestionQuizzDto toDto (QuestionQuizzEntity questionQuizzEntity){

        return QuestionQuizzDto.builder()
                .id(questionQuizzEntity.getId())
                .questionText(questionQuizzEntity.getQuestionText())
                .timeMinutes(questionQuizzEntity.getTimeMinutes())
                .difficulty(questionQuizzEntity.getDifficulty())
                .idLecture(questionQuizzEntity.getLecture().getId())
                .idProfessor(questionQuizzEntity.getProfessor().getIdUser())
                .answersQuestion(
                        questionQuizzEntity.getAnswersQuestion().stream().map(AnswerQuizzMapper::toDto)
                                .collect(Collectors.toList())
                        )
                .build();
    }

    public static QuestionQuizzEntity fromCreationDto(QuestionQuizzCreationDto questionQuizzDto) {

            final List<AnswerQuizzCreationDto> answersQuizzDtoList = questionQuizzDto.getAnswersQuestion();

            final List<AnswerQuizzEntity> answersQuizzEntityList = new ArrayList<>();

            QuestionQuizzEntity questionQuizzEntity =
                    QuestionQuizzEntity.builder()
                    .questionText(questionQuizzDto.getQuestionText())
                    .answersQuestion(answersQuizzEntityList)
                    .build();
            if(questionQuizzDto.getTimeMinutes() != null){
                questionQuizzEntity.setTimeMinutes(questionQuizzDto.getTimeMinutes());
            }
            else{
                questionQuizzEntity.setTimeMinutes(1.00);
            }
            if(questionQuizzDto.getDifficulty() != null){
                questionQuizzEntity.setDifficulty(questionQuizzDto.getDifficulty());
            }
            else {
                questionQuizzEntity.setDifficulty(5);
            }

            answersQuizzDtoList.forEach(answer -> {
                AnswerQuizzEntity answerQuizzEntity = AnswerQuizzMapper.fromCreationDto(answer, questionQuizzEntity);
                if(answer.isCorrect()) {
                    answerQuizzEntity.setCorrectAnswerQuizz(CorrectAnswerQuizzMapper.fromAnswerQuizzEntity(answerQuizzEntity));
                }
                answersQuizzEntityList.add(answerQuizzEntity);
            });

            return questionQuizzEntity;
    }

}
