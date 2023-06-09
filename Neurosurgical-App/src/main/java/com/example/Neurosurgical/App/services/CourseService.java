package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    List<CourseDto> findAll();
    void deleteCourse(Long id);
    Optional<CourseDto> findById(Long id) throws EntityNotFoundException;
    void createCourse(CourseCreationDto courseCreationDto) throws UserAlreadyExistsException;
    void updateCourse(Long id, CourseCreationDto courseCreationDto);
    Optional<CourseDto> findByTitle(String code) throws EntityNotFoundException;
    Optional<CourseDto> findByLectureId(Long id) throws EntityNotFoundException;
    List<CourseDto> findAllByProfessorId(Long id);
    List<CourseDto> findAllByStudentId(Long id);
}
