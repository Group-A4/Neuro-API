package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.StudentCreationDto;
import com.example.Neurosurgical.App.models.dtos.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    List<StudentDto> findAll();
    void deleteStudent(Long id);
    Optional<StudentDto> findById(Long id) throws UserNotFoundException;
    void createStudent(StudentCreationDto studentCreationDto) throws UserAlreadyExistsException;
    void updateStudent(Long id, StudentDto studentDto);
    Optional<StudentDto> findByCode(String code) throws UserNotFoundException;
//    List<CourseEntity> findCoursesStudentFollows(Long id);
    List<StudentDto> findByCourseId(Long id);
}
