package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionMultipleChoiceExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionLongResponseExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionMultipleChoiceExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionLongResponseExamCreationDto;
import com.example.Neurosurgical.App.services.QuestionExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/questionExam")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionExamController {
    private final QuestionExamService questionExamService;

    @Autowired
    public QuestionExamController(QuestionExamService questionExamService){
        this.questionExamService = questionExamService;
    }

    @GetMapping(value = "/multipleChoice", produces="application/json")
    public List<QuestionMultipleChoiceExamDto> getAllMultipleChoice() {
        return questionExamService.findAllMultipleChoice();
    }

    @GetMapping(value = "/longResponse", produces="application/json")
    public List<QuestionLongResponseExamDto> getAllLongResponse() {
        return questionExamService.findAllLongResponse();
    }

    @PostMapping(value = "/multipleChoice/create/idExam={idExam}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMultipleChoiceQuestionExam (@RequestBody @Valid QuestionMultipleChoiceExamCreationDto questionMultipleChoiceExamCreationDto, @PathVariable Long idExam) throws EntityNotFoundException {
        questionExamService.createMultipleChoiceQuestionExam(questionMultipleChoiceExamCreationDto, idExam);
    }

    @PostMapping(value = "/longResponse/create/idExam={idExam}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLongResponseQuestionExam (@RequestBody @Valid QuestionLongResponseExamCreationDto questionLongResponseDto, @PathVariable Long idExam) throws EntityNotFoundException {
        questionExamService.createLongResponseQuestionExam(questionLongResponseDto, idExam);
    }

    @PutMapping(value = "/multipleChoice/update/idQuestion={idQuestion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestionExam(@RequestBody @Valid QuestionMultipleChoiceExamDto questionExamCreationDto, @PathVariable Long idQuestion) throws EntityNotFoundException {
        questionExamService.updateMultipleChoiceQuestionExam(questionExamCreationDto, idQuestion);
    }

    @PutMapping(value = "/longResponse/update/idQuestion={idQuestion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestionExam(@RequestBody @Valid QuestionLongResponseExamCreationDto questionExamCreationDto, @PathVariable Long idQuestion) throws EntityNotFoundException {
        questionExamService.updateLongResponseQUestionExam(questionExamCreationDto, idQuestion);
    }

    @DeleteMapping(value = "/delete/idQuestion={idQuestion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionExam(@PathVariable Long idQuestion) throws EntityNotFoundException {
        questionExamService.deleteQuestionExam(idQuestion);
    }

}
