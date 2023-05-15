package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionMultipleChoiceExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionLongResponseExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionMultipleChoiceExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionLongResponseExamCreationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionExamService {
    List<QuestionMultipleChoiceExamDto> findAllMultipleChoice();

    List<QuestionLongResponseExamDto> findAllLongResponse();
    void createMultipleChoiceQuestionExam(QuestionMultipleChoiceExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException;
    void createLongResponseQuestionExam(QuestionLongResponseExamCreationDto questionLongResponseDto, Long idExam) throws EntityNotFoundException;

    void updateMultipleChoiceQuestionExam(QuestionMultipleChoiceExamDto questionExamCreationDto, Long idQuestion) throws EntityNotFoundException;

    void updateLongResponseQUestionExam(QuestionLongResponseExamCreationDto questionExamCreationDto, Long idQuestion);

    void deleteQuestionExam(Long idQuestion) throws EntityNotFoundException;
}
