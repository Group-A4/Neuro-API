package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.CourseMapper;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CourseServiceImpl implements CourseService {

    public final CourseRepository courseRepository;
    public final MaterialRepository materialRepository;
    public final ProfessorRepository professorRepository;
    public final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, MaterialRepository materialRepository, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.materialRepository = materialRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<CourseDto> findById(Long id) throws UserNotFoundException {
        CourseDto courseDto = CourseMapper.toDto(courseRepository.findById(id).get());
        return Optional.of(courseDto);
    }

    @Override
    public void createCourse(CourseEntity courseEntity) throws UserAlreadyExistsException {
        courseRepository.save(courseEntity);
    }

    @Override
    public void updateCourse(Long id, CourseEntity courseEntity) {
        checkIfExists(id);
        // save() -- when we send an object without id, it adds directly a row in database,
        // but if we send an object with an existing id,
        // it changes the columns already found in the database.
        courseEntity.setId(id);
        courseRepository.save(courseEntity);
    }

    @Override
    public Optional<CourseDto> findByCode(String code) throws UserNotFoundException {

        Optional<CourseEntity> courseEntity = Optional.of(courseRepository.findByCode(code));

        if(courseEntity.isEmpty()) throw new EntityNotFoundException("course", code);

        return Optional.of(CourseMapper.toDto(courseEntity.get()));
    }

    @Override
    public Optional<CourseDto> findByTitle(String title) throws UserNotFoundException {
        CourseEntity courseEntity = courseRepository.findByTitle(title);

        if(courseEntity == null) throw new EntityNotFoundException("course", title);

        return Optional.of(CourseMapper.toDto(courseEntity));
    }
    public void checkIfExists(Long id) {
        if (courseRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Course", id);
        }
    }

    @Override
    public Optional<CourseDto> findByMaterialId(Long id) throws UserNotFoundException {
        CourseEntity courseEntity = materialRepository.findById(id).get().getCourse();
        return Optional.of(CourseMapper.toDto(courseEntity));
    }

    @Override
    public List<CourseDto> findAllByProfessorId(Long id) {
        return professorRepository.findById(id).get().getTeachings()
                .stream()
                .map(teaching -> CourseMapper.toDto(teaching.getCourse()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findAllByStudentId(Long id) {
        return studentRepository.findById(id).get().getEnrollments()
                .stream()
                .map(enrollment -> CourseMapper.toDto(enrollment.getCourse()))
                .collect(Collectors.toList());
    }
}
