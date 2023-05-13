package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import com.example.Neurosurgical.App.services.QuestionQuizzService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questionQuizz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionQuizzController {
    private final QuestionQuizzService questionQuizzService;

    @Autowired
    public QuestionQuizzController(QuestionQuizzService questionQuizzService){
        this.questionQuizzService = questionQuizzService;
    }

    @GetMapping(value = "", produces="application/json")
    public List<QuestionQuizzDto> getAll() {
        return questionQuizzService.findAll();
    }

    @GetMapping(value = "/{id}", produces="application/json")
    public Optional<QuestionQuizzDto> getById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionQuizzService.findById(id);
    }

    @GetMapping(value = "/professor={id}", produces="application/json")
    public Optional<List<QuestionQuizzDto>> getByIdProfessor(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionQuizzService.findByIdProfessor(id);
    }

    @GetMapping(value = "/course={id}", produces="application/json")
    public Optional<List<QuestionQuizzDto>> getByIdCourse(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return questionQuizzService.findByIdCourse(id);
    }

    @GetMapping(value = "/professor={idProfessor}/course={idCourse}", produces="application/json")
    public Optional<List<QuestionQuizzDto>> getByIdProfessorAndIdCourse(@PathVariable @Valid @Min(0) Long idProfessor,
                                                              @PathVariable @Valid @Min(0) Long idCourse) throws EntityNotFoundException {
        return questionQuizzService.findByIdProfessorAndIdCourse(idProfessor, idCourse);
    }

    @GetMapping(value = "/course={idCourse}/lecture={idLecture}", produces="application/json")
    public Optional<List<QuestionQuizzDto>> getByIdCourseAndLectureNumber(@PathVariable @Valid @Min(0) Long idCourse,
                                                              @PathVariable @Valid @Min(0) Long idLecture) throws EntityNotFoundException {
        return questionQuizzService.findByIdCourseAndLectureNumber(idCourse, idLecture);
    }

    @PostMapping(value = "/create", produces="application/json")
    public void createQuestionQuizz(@RequestBody QuestionQuizzCreationDto questionQuizzDto) {
        questionQuizzService.createQuestionQuizz(questionQuizzDto);
    }

    @DeleteMapping(value = "/delete/{id}", produces="application/json")
    public void deleteQuestionQuizzById(@PathVariable @Valid @Min(0) Long id) {
        questionQuizzService.deleteQuestionQuizzById(id);
    }

    @PutMapping(value = "/update/{id}", produces="application/json")
    public void updateQuestionQuizz(@PathVariable @Valid @Min(0) Long id, @RequestBody QuestionQuizzDto questionQuizzDto) {
        questionQuizzService.updateQuestionQuizz(id, questionQuizzDto);
    }

    @PutMapping(value = "/updateText/{id}", produces="application/json")
    public void updateQuestionQuizzText(@PathVariable @Valid @Min(0) Long id, @RequestBody String questionText) {
        questionQuizzService.updateQuestionQuizzText(id, questionText);
    }
}
