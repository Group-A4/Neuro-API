package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.services.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/courses")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;

    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> getAll(){
        return courseService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CourseDto> getById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        Optional<CourseDto> courseDto = courseService.findById(id);
        if(courseDto.isPresent()){
            return courseDto;
        } else {
            throw new EntityNotFoundException("Course", id);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseById(@PathVariable @Valid @Min(0) Long id){
        courseService.deleteCourse(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody @Valid CourseCreationDto courseCreationDto){
        courseService.createCourse(courseCreationDto);
    }

    @GetMapping(value = "/title={title}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CourseDto> getByTitle(@PathVariable @Valid String title) throws EntityNotFoundException {
        Optional<CourseDto> courseDto = courseService.findByTitle(title);
        if(courseDto.isPresent()){
            return courseDto;
        } else {
            throw new EntityNotFoundException("Course", title);
        }
    }

    @GetMapping(value = "/code={code}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CourseDto> getByCode(@PathVariable @Valid String code) throws EntityNotFoundException {
        Optional<CourseDto> courseDto = courseService.findByCode(code);
        if(courseDto.isPresent()){
            return courseDto;
        } else {
            throw new EntityNotFoundException("Course", code);
        }
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid CourseCreationDto courseCreationDto) {
        courseService.updateCourse(id, courseCreationDto);
    }

    @GetMapping(value="/lecture={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CourseDto> getByLectureId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        Optional<CourseDto> courseDto = courseService.findByLectureId(id);
        if(courseDto.isPresent()){
            return courseDto;
        } else {
            throw new EntityNotFoundException("Course with material id= ", id);
        }
    }
    @GetMapping(value="/professor={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> getAllByProfessorId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        List<CourseDto> courseDto = courseService.findAllByProfessorId(id);
        if(courseDto.isEmpty()){
            throw new EntityNotFoundException("Courses with professor id= ", id);
        } else {
            return courseDto;
        }
    }

    @GetMapping(value="/student={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> getAllByStudentId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        List<CourseDto> courseDto = courseService.findAllByStudentId(id);
        if(courseDto.isEmpty()){
            throw new EntityNotFoundException("Courses with student id= ", id);
        } else {
            return courseDto;
        }
    }
}
