package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.services.QuestionExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/questionExam")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionExamController {
    private final QuestionExamService questionExamService;

    @Autowired
    public QuestionExamController(QuestionExamService questionExamService){
        this.questionExamService = questionExamService;
    }

    @GetMapping(value = "", produces="application/json")
    public List<QuestionExamDto> getAll() {
        return questionExamService.findAll();
    }

    @PostMapping(value = "/create/idExam={idExam}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestionExam (@RequestBody @Valid QuestionExamCreationDto questionExamCreationDto, @PathVariable Long idExam) throws EntityNotFoundException {
        questionExamService.createQuestionExam(questionExamCreationDto, idExam);
    }

    @PutMapping(value = "/update/idQuestion={idQuestion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestionExam(@RequestBody @Valid QuestionExamDto questionExamCreationDto, @PathVariable Long idQuestion) throws EntityNotFoundException {
        questionExamService.updateQuestionExam(questionExamCreationDto, idQuestion);
    }

    @DeleteMapping(value = "/delete/idQuestion={idQuestion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionExam(@PathVariable Long idQuestion) throws EntityNotFoundException {
        questionExamService.deleteQuestionExam(idQuestion);
    }

}
