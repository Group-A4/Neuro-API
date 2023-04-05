//package com.example.Neurosurgical.App.services;
//
//import com.example.Neurosurgical.App.dao.StudentDao;
//import com.example.Neurosurgical.App.dao.UserDao;
//import com.example.Neurosurgical.App.exception.UserNotFoundException;
//import com.example.Neurosurgical.App.mappers.StudentMapper;
//import com.example.Neurosurgical.App.model.dto.StudentDto;
//import com.example.Neurosurgical.App.model.dto.UserDto;
//import com.example.Neurosurgical.App.model.entity.StudentEntity;
//import com.example.Neurosurgical.App.model.entity.UserEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public class StudentServiceImpl implements StudentService{
//
//    private final StudentDao studentDao;
//
//    private final UserDao userDao;
//
//    @Autowired
//    public StudentServiceImpl(StudentDao studentDao, UserDao userDao) {
//        this.studentDao = studentDao;
//        this.userDao = userDao;
//    }
//
//    @Override
//    public List<StudentDto> findAll() {
//        List<StudentDto> studentDtos = studentDao.findAll().stream()
//                .map(StudentMapper::toDto)
//                .collect(Collectors.toList());
//
//
//
//        return studentDao.findAll();
//    }
//
//    @Override
//    public void deleteUser(Long id){
//        Long idUser = studentDao.findUserByFK(id);
//        studentDao.deleteById(id);
//        userDao.deleteById(idUser);
//    }
//
//    @Override
//    public Optional<StudentDto> findById(Long id) throws UserNotFoundException {
//        return Optional.empty();
//    }
//}
