package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.ErrorResponse;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.dtos.StudentCreationDto;
import com.example.Neurosurgical.App.models.dtos.StudentDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.services.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getAll(){
        return studentService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<StudentDto> getById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        Optional<StudentDto> studentDto = studentService.findById(id);
        if(studentDto.isPresent()){
            return studentDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping(value = "/code/{code}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<StudentDto> getByCode(@PathVariable @Valid @Min(0) String code) throws UserNotFoundException {
        Optional<StudentDto> studentDto = studentService.findByCode(code);
        if(studentDto.isPresent()){
            return studentDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id){
        studentService.deleteStudent(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody @Valid StudentCreationDto studentCreationDto) throws UserAlreadyExistsException {
        studentService.createStudent(studentCreationDto);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStudent(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid StudentDto studentDto) {
        studentService.updateStudent(id, studentDto);
    }

    @GetMapping(value = "/course={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getByCourseId(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        List<StudentDto> studentDto = studentService.findByCourseId(id);
        if(studentDto.isEmpty()){
            throw new UserNotFoundException();
        } else {
            return studentDto;
        }
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex)
    {
        return new ErrorResponse(ex.getMessage());
    }
}
