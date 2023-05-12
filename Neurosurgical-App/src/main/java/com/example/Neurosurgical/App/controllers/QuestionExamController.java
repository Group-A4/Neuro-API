package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.services.QuestionExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questionExam")
public class QuestionExamController {
    private final QuestionExamService questionExamService;

    @Autowired
    public QuestionExamController(QuestionExamService questionExamService){
        this.questionExamService = questionExamService;
    }

    @GetMapping(value = "", produces="application/json")
    public List<QuestionExamDto> getAll(){
        return questionExamService.findAll();
    }

    @GetMapping(value = "/{id}", produces="application/json")
    public Optional<QuestionExamDto> getById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionExamService.findById(id);
    }

    @GetMapping(value = "/professor={id}", produces="application/json")
    public Optional<List<QuestionExamDto>> getByIdProfessor(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionExamService.findByIdProfessor(id);
    }

    @GetMapping(value = "/course={id}", produces="application/json")
    public Optional<List<QuestionExamDto>> getByIdCourse(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionExamService.findByIdCourse(id);
    }

    @GetMapping(value = "/professor={id}/course={idCourse}", produces="application/json")
    public Optional<List<QuestionExamDto>> getByIdProfessorAndIdCourse(@PathVariable @Valid @Min(0) Long idProfessor,
                                                                       @PathVariable @Valid @Min(0) Long idCourse) throws EntityNotFoundException {
        return questionExamService.findByIdProfessorAndIdCourse(idProfessor, idCourse);
    }

    @PostMapping(value = "/create", produces="application/json")
    public void createExamQuestion(@RequestBody QuestionExamDto questionExamDto){
        questionExamService.createExamQuestion(questionExamDto);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public void deleteExamQuestionById(@PathVariable @Valid @Min(0) Long id) {
        questionExamService.deleteExamQuestionById(id);
    }

    @PutMapping(value = "/update/{id}", produces="application/json")
    public void updateExamQuestion(@PathVariable @Valid @Min(0) Long id, @RequestBody QuestionExamDto questionExamDto){
        questionExamService.updateExamQuestion(id, questionExamDto);
    }

    @PutMapping(value = "/updateText/{id}", produces="application/json")
    public void updateExamQuestionText(@PathVariable @Valid @Min(0) Long id, @RequestBody String questionText){
        questionExamService.updateExamQuestionText(id, questionText);
    }
}
