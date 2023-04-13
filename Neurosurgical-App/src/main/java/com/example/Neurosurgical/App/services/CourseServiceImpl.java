package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.CourseMapper;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CourseServiceImpl implements CourseService {

    public final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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

//    @Override
//    public Optional<CourseDto> findByMaterial(Long id) throws UserNotFoundException {
//        CourseEntity courseEntity = courseRepository.findByMaterial(id);
//
//        if(courseEntity == null) throw new EntityNotFoundException("course", id);
//
//        return Optional.of(CourseMapper.toDto(courseEntity));
//    }
}
