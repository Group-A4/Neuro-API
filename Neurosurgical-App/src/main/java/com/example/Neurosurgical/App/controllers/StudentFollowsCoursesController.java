package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.services.StudentFollowsCoursesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/studentFollowsCourses")
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class StudentFollowsCoursesController {
    private final StudentFollowsCoursesService studentFollowsCoursesService;
    @Autowired
    public StudentFollowsCoursesController(StudentFollowsCoursesService studentFollowsCoursesService) {
        this.studentFollowsCoursesService = studentFollowsCoursesService;
    }

    @DeleteMapping("/delete/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteStudentFollowsCourse(@PathVariable @Valid @Min(0) Long Id){
        studentFollowsCoursesService.deleteStudentFollowsCourses(Id);
    }

    @DeleteMapping("/delete/course={courseId}/student={studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteStudentFollowsCourse(@PathVariable @Valid @Min(0) Long courseId, @PathVariable @Valid @Min(0) Long studentId){
        studentFollowsCoursesService.deleteStudentFollowsCourses(courseId, studentId);
    }

    @PostMapping("/create/course={code}/student={studentId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT')")
    public void createStudentFollowsCourse(@PathVariable @Valid String code, @PathVariable @Valid @Min(0) Long studentId){
        studentFollowsCoursesService.createStudentFollowsCourses(code, studentId);
    }

}
