package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ProfessorMapper;
import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.StudentMapper;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.models.dtos.StudentCreationDto;
import com.example.Neurosurgical.App.models.dtos.StudentDto;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentDao, UserRepository userDao, CourseRepository courseRepository) {
        this.studentRepository = studentDao;
        this.userRepository = userDao;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<StudentDto> findAll() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        List<UserEntity> userEntities = userRepository.findAll();

        List<StudentDto> studentDtos = new ArrayList<>();

        for(var stud : studentEntities){
            UserEntity userEntity = userEntities.stream()
                    .filter(x -> Objects.equals(x.getId(), stud.getIdUser()))
                    .findAny().get();

            studentDtos.add(StudentMapper.toDto(userEntity, stud));
        }

        return studentDtos;
    }

    @Override
    public void deleteStudent(Long id){
        checkIfExists(id);
        studentRepository.deleteById(id);
        userRepository.deleteById(id);
    }

    @Override
    public Optional<StudentDto> findById(Long id) throws UserNotFoundException {
        return Optional.of(StudentMapper.toDto(userRepository.findById(id).get(), studentRepository.findById(id).get()));
    }

    @Override
    @Transactional
    public void createStudent(StudentCreationDto studentCreationDto) throws UserAlreadyExistsException{
        if(userRepository.findByFacMail(studentCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userRepository.findByPersonalMail(studentCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(studentRepository.findByCode(studentCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user;
        try {
            user = UserMapper.fromStudentCreationDtoToUserEntity(studentCreationDto);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User already exists or the input is invalid!");
        }

        try {
            StudentEntity studentEntity = StudentEntity.builder()
                    .idUser(userRepository.findByFacMail(user.getEmailFaculty()).getId())
                    .code(studentCreationDto.getCode())
                    .year(studentCreationDto.getYear())
                    .semester(studentCreationDto.getSemester())
                    .birthDate(studentCreationDto.getBirthDate())
                    .build();

            studentRepository.save(studentEntity);
        } catch (Exception e) {
            userRepository.deleteById(user.getId());
            throw new UserAlreadyExistsException("User already exists or the input is invalid!");
        }
    }

    @Override
    public void updateStudent(Long id, StudentDto studentDto){
        checkIfExists(id);
        StudentEntity studentToUpdate = StudentMapper.fromDto(studentDto);
        studentToUpdate.setIdUser(id);
        studentRepository.save(studentToUpdate);

        UserDto userToUpdate = UserMapper.fromStudentDtoToUserDto(studentDto);
        new UserServiceImpl(userRepository).updateUser(id, userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Student", id);
        }
    }

    @Override
    public Optional<StudentDto> findByCode(String code) throws UserNotFoundException {
        StudentEntity studentEntity = studentRepository.findByCode(code);
        UserEntity userEntity = userRepository.findById(studentEntity.getIdUser()).get();

        return Optional.of(StudentMapper.toDto(userEntity, studentEntity));
    }

    @Override
    public List<StudentDto> findByCourseId(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id).get();
        List<StudentEntity> studentEntities = courseEntity.getRegistrations().stream().map(x -> x.getStudent()).toList();
        List<UserEntity> users = userRepository.findAll();

        List<StudentDto> studentDtos = new ArrayList<>();
        for(var stud : studentEntities){
            UserEntity userEntity = users.stream()
                    .filter(x -> Objects.equals(x.getId(), stud.getIdUser()))
                    .findAny().get();

            studentDtos.add(StudentMapper.toDto(userEntity, stud));
        }

        return studentDtos;
    }
}
