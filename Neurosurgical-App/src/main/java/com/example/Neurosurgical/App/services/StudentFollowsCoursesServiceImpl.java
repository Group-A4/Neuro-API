package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.DidacticEntity;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import com.example.Neurosurgical.App.models.entities.StudentFollowsCoursesEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.StudentFollowsCoursesRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentFollowsCoursesServiceImpl implements StudentFollowsCoursesService{
    private final StudentFollowsCoursesRepository studentFollowsCoursesRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentFollowsCoursesServiceImpl(StudentFollowsCoursesRepository studentFollowsCoursesRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentFollowsCoursesRepository = studentFollowsCoursesRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void deleteStudentFollowsCourses(Long id) {
        Optional<StudentFollowsCoursesEntity> studentFollowsCoursesEntityOptional = studentFollowsCoursesRepository.findById(id);
        if (studentFollowsCoursesEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("StudentFollowsCourses not found");
        }
        studentFollowsCoursesRepository.delete(studentFollowsCoursesEntityOptional.get());
    }

    @Override
    public void createStudentFollowsCourses(String code, Long studentId) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        CourseEntity courseEntityOptional = courseRepository.findByCode(code);

        if (studentEntityOptional.isEmpty() || courseEntityOptional == null) {
            throw new EntityNotFoundException("Student or course not found");
        }

        try {
            studentFollowsCoursesRepository.save(new StudentFollowsCoursesEntity(studentEntityOptional.get(), courseEntityOptional));
        } catch (Exception e) {
            throw new EntityAlreadyExistsException("studentFollowsCourses", "studentFollowsCourses");
        }
    }

    @Override
    public void deleteStudentFollowsCourses(Long courseId, Long studentId) {
        Optional<StudentEntity> studentEntityOptional = studentRepository.findById(studentId);
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (studentEntityOptional.isEmpty() || courseEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Student or course not found");
        }
        studentFollowsCoursesRepository.delete(studentFollowsCoursesRepository.findByStudentIdAndCourseId(studentId, courseId));
    }
}
