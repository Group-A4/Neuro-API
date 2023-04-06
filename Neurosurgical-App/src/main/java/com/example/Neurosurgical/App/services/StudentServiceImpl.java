package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.repository.StudentDao;
import com.example.Neurosurgical.App.repository.UserDao;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.StudentMapper;
import com.example.Neurosurgical.App.model.dto.StudentCreationDto;
import com.example.Neurosurgical.App.model.dto.StudentDto;
import com.example.Neurosurgical.App.model.entity.StudentEntity;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDao studentDao;
    private final UserDao userDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, UserDao userDao) {
        this.studentDao = studentDao;
        this.userDao = userDao;
    }

    @Override
    public List<StudentDto> findAll() {
        List<StudentEntity> studentEntities = studentDao.findAll();
        List<UserEntity> userEntities = userDao.findAll();

        List<StudentDto> studentDtos = new ArrayList<>();

        for(var stud : studentEntities){
            StudentDto studentDto = StudentMapper.toDto(stud);
            UserEntity userEntity = userEntities.stream()
                    .filter(x -> Objects.equals(x.getId(), stud.getIdUser()))
                    .findAny().get();

            studentDto.setEmailFaculty(userEntity.getEmailFaculty());
            studentDto.setEmailPersonal(userEntity.getEmailPersonal());
            studentDto.setFirstName(userEntity.getFirstName());
            studentDto.setLastName(userEntity.getLastName());
            studentDtos.add(studentDto);
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
        StudentDto studentDto = StudentMapper.toDto(studentDao.findById(id).get());
        UserEntity userEntity = userDao.findById(id).get();

        studentDto.setEmailFaculty(userEntity.getEmailFaculty());
        studentDto.setEmailPersonal(userEntity.getEmailPersonal());
        studentDto.setFirstName(userEntity.getFirstName());
        studentDto.setLastName(userEntity.getLastName());

        return Optional.of(studentDto);
    }

    @Override
    public void createStudent(StudentCreationDto studentCreationDto) throws UserAlreadyExistsException{
        if(userDao.findByFacMail(studentCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userDao.findByPersonalMail(studentCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(studentDao.findByCode(studentCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user = UserEntity.builder()
                .firstName(studentCreationDto.getFirstName())
                .lastName(studentCreationDto.getLastName())
                .emailFaculty(studentCreationDto.getEmailFaculty())
                .emailPersonal(studentCreationDto.getEmailPersonal())
                .password(studentCreationDto.getPassword())
                .role(2)
                .build();

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
    public Optional<StudentDto> findByCode(String code) throws UserNotFoundException {
        StudentEntity studentEntity = studentDao.findByCode(code);
        UserEntity userEntity = userDao.findById(studentEntity.getIdUser()).get();

        StudentDto studentDto = StudentMapper.toDto(studentEntity);
        studentDto.setFirstName(userEntity.getFirstName());
        studentDto.setLastName(userEntity.getLastName());
        studentDto.setEmailFaculty(userEntity.getEmailFaculty());
        studentDto.setEmailPersonal(userEntity.getEmailPersonal());

        return Optional.of(studentDto);
    }
}
