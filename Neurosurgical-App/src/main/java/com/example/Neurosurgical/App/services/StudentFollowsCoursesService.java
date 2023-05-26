package com.example.Neurosurgical.App.services;

import org.springframework.stereotype.Service;

@Service
public interface StudentFollowsCoursesService {
    void deleteStudentFollowsCourses(Long id);
    void createStudentFollowsCourses(String code, Long studentId);
    void deleteStudentFollowsCourses(Long courseId, Long studentId);
}
