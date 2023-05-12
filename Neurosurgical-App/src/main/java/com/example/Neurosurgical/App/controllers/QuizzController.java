package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.services.QuizzService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quizz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizzController {
    private final QuizzService quizzService;
    @Autowired
    public QuizzController(QuizzService quizzService) {
        this.quizzService = quizzService;
    }

    @GetMapping(value = "/course={id}", produces = "application/json")
    public Optional<List<QuestionQuizzDto>> getQuizzByCourseId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return quizzService.findByCourseId(id);
    }


}
