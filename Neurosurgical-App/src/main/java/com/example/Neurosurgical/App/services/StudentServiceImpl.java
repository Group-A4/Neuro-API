package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.StudentMapper;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.models.dtos.StudentCreationDto;
import com.example.Neurosurgical.App.models.dtos.StudentDto;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentDao;
    private final UserRepository userDao;

    @Autowired
    public StudentServiceImpl(StudentRepository studentDao, UserRepository userDao) {
        this.studentDao = studentDao;
        this.userDao = userDao;
    }

    @Override
    public List<StudentDto> findAll() {
        List<StudentEntity> studentEntities = studentDao.findAll();
        List<UserEntity> userEntities = userDao.findAll();

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
        studentDao.deleteById(id);
        userDao.deleteById(id);
    }

    @Override
    public Optional<StudentDto> findById(Long id) throws UserNotFoundException {
        return Optional.of(StudentMapper.toDto(userDao.findById(id).get(), studentDao.findById(id).get()));
    }

    @Override
    public void createStudent(StudentCreationDto studentCreationDto) throws UserAlreadyExistsException{
        if(userDao.findByFacMail(studentCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userDao.findByPersonalMail(studentCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(studentDao.findByCode(studentCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user = UserMapper.fromStudentCreationDtoToUserEntity(studentCreationDto);
        userDao.save(user);

        StudentEntity studentEntity = StudentEntity.builder()
                .idUser(userDao.findByFacMail(user.getEmailFaculty()).getId())
                .code(studentCreationDto.getCode())
                .year(studentCreationDto.getYear())
                .semester(studentCreationDto.getSemester())
                .birthDate(studentCreationDto.getBirthDate())
                .build();

        studentDao.save(studentEntity);
    }

    @Override
    public void updateStudent(Long id, StudentDto studentDto){
        checkIfExists(id);
        StudentEntity studentToUpdate = StudentMapper.fromDto(studentDto);
        studentToUpdate.setIdUser(id);
        studentDao.save(studentToUpdate);

        UserDto userToUpdate = UserMapper.fromStudentDtoToUserDto(studentDto);
        new UserServiceImpl(userDao).updateUser(id, userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (studentDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Student", id);
        }
    }

    @Override
    public Optional<StudentDto> findByCode(String code) throws UserNotFoundException {
        StudentEntity studentEntity = studentDao.findByCode(code);
        UserEntity userEntity = userDao.findById(studentEntity.getIdUser()).get();

        return Optional.of(StudentMapper.toDto(userEntity, studentEntity));
    }
}
