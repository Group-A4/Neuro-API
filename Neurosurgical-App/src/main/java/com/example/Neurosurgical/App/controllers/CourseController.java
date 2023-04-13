package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.services.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping("/courses")
@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;

    }

    @GetMapping(value = "", produces = "application/json")
    public List<CourseEntity> getAll(){
        return courseService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<CourseDto> getById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return courseService.findById(id);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deletCourseById(@PathVariable @Valid @Min(0) Long id){
        courseService.deleteCourse(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void createCourse(@RequestBody @Valid CourseEntity courseEntity){
        courseService.createCourse(courseEntity);
    }

    @GetMapping(value = "/title={title}", produces = "application/json")
    public Optional<CourseDto> findByTitle(@PathVariable @Valid String title) throws EntityNotFoundException {
        return courseService.findByTitle(title);
    }

    @GetMapping(value = "/code={code}", produces = "application/json")
    public Optional<CourseDto> findByCode(@PathVariable @Valid String code) throws EntityNotFoundException {
        return courseService.findByCode(code);
    }
    @PutMapping("/update/{id}")
    public void updateCourse(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid CourseEntity courseEntity) {
        courseService.updateCourse(id, courseEntity);
    }


}
