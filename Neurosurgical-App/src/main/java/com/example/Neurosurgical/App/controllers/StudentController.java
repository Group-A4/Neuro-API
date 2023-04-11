package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import com.example.Neurosurgical.App.model.dto.StudentCreationDto;
import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.services.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "", produces = "application/json")
    public List<StudentDto> getAll(){
        return studentService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<StudentDto> getById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        return studentService.findById(id);
    }

    @GetMapping(value = "/code/{code}", produces = "application/json")
    public Optional<StudentDto> getByCode(@PathVariable @Valid @Min(0) String code) throws UserNotFoundException {
        return studentService.findByCode(code);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id){
        studentService.deleteStudent(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void createStudent(@RequestBody @Valid StudentCreationDto studentCreationDto) throws UserAlreadyExistsException {
        studentService.createStudent(studentCreationDto);
    }

    @PutMapping("update/{id}")
    public void updateStudent(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid StudentDto studentDto) {
        studentService.updateStudent(id, studentDto);
    }
}
