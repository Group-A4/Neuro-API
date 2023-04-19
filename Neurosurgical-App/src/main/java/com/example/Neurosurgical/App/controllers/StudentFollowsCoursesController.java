package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.services.StudentFollowsCoursesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/studentFollowsCourses")
@RestController
public class StudentFollowsCoursesController {
    private final StudentFollowsCoursesService studentFollowsCoursesService;
    @Autowired
    public StudentFollowsCoursesController(StudentFollowsCoursesService studentFollowsCoursesService) {
        this.studentFollowsCoursesService = studentFollowsCoursesService;
    }

    @DeleteMapping("/delete/{Id}")
    public void deleteStudentFollowsCourse(@PathVariable @Valid @Min(0) Long Id){
        studentFollowsCoursesService.deleteStudentFollowsCourses(Id);
    }

    @DeleteMapping("/delete/course={courseId}/student={studentId}")
    public void deleteStudentFollowsCourse(@PathVariable @Valid @Min(0) Long courseId, @PathVariable @Valid @Min(0) Long studentId){
        studentFollowsCoursesService.deleteStudentFollowsCourses(courseId, studentId);
    }

    @PostMapping("/create/course={courseId}/student={studentId}")
    public void createStudentFollowsCourse(@PathVariable @Valid @Min(0) Long courseId, @PathVariable @Valid @Min(0) Long studentId){
        studentFollowsCoursesService.createStudentFollowsCourses(courseId, studentId);
    }

}
