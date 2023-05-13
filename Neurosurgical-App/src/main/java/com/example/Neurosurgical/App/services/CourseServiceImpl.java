package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.CourseMapper;
import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    public final CourseRepository courseRepository;
    public final LectureRepository lectureRepository;
    public final ProfessorRepository professorRepository;
    public final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, LectureRepository lectureRepository, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
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
        checkIfExists(id);
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<CourseDto> findById(Long id) throws EntityNotFoundException {
        CourseDto courseDto = CourseMapper.toDto(courseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Course",id)));
        return Optional.of(courseDto);
    }

    @Override
    public void createCourse(CourseCreationDto courseCreationDto) {
        try{
            courseRepository.save(CourseMapper.fromCreationDto(courseCreationDto));
        }catch (Exception e){
            throw new EntityAlreadyExistsException("Course",courseCreationDto.getTitle());
        }

    }

    @Override
    public void updateCourse(Long id, CourseCreationDto courseCreationDto) {
        checkIfExists(id);
        CourseEntity courseEntity = CourseMapper.fromCreationDto(courseCreationDto);
        courseEntity.setId(id);
        courseRepository.save(courseEntity);
    }

    @Override
    public Optional<CourseDto> findByCode(String code) throws EntityNotFoundException {

        CourseEntity courseEntity = Optional.ofNullable(courseRepository.findByCode(code))
                .orElseThrow(() -> new EntityNotFoundException("Course", code));

        return Optional.of(CourseMapper.toDto(courseEntity));
    }

    @Override
    public Optional<CourseDto> findByTitle(String title) throws EntityNotFoundException {

        CourseEntity courseEntity = Optional.ofNullable(courseRepository.findByTitle(title))
                .orElseThrow(() -> new EntityNotFoundException("Course", title));

        return Optional.of(CourseMapper.toDto(courseEntity));
    }
    public void checkIfExists(Long id) {
        if (courseRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Course", id);
        }
    }

    @Override
    public Optional<CourseDto> findByLectureId(Long id) throws EntityNotFoundException {

        LectureEntity lectureEntity = Optional.of(lectureRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException("Lecture",id )).get();

        CourseEntity courseEntity = Optional.ofNullable(lectureEntity.getCourse())
                .orElseThrow(() -> new EntityNotFoundException("Course with lecture: ", lectureEntity.getTitle()));

        return Optional.of(CourseMapper.toDto(courseEntity));
    }

    @Override
    public List<CourseDto> findAllByProfessorId(Long id) {

        ProfessorEntity professorEntity = Optional.of(professorRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException("Material",id)).get() ;

        //check if professor has teachings
        if(professorEntity.getTeachings().isEmpty()){
            return new ArrayList<CourseDto>();
        }

        return professorEntity.getTeachings()
                .stream()
                .map(teaching -> CourseMapper.toDto(teaching.getCourse()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findAllByStudentId(Long id) {

        StudentEntity studentEntity = Optional.of(studentRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException("Student",id)).get() ;

        //check if student has enrollments
        if(studentEntity.getEnrollments().isEmpty()){
            return new ArrayList<CourseDto>();
        }
        return studentEntity.getEnrollments()
                .stream()
                .map(enrollment -> CourseMapper.toDto(enrollment.getCourse()))
                .collect(Collectors.toList());
    }
}
