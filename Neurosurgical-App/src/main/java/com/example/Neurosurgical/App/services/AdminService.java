package com.example.Neurosurgical.App.services;


import com.example.Neurosurgical.App.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.UserStudentDTOMapper;
import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.models.enums.UserRole;
import com.example.Neurosurgical.App.repositories.AdminRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    private final StudentRepository studentRepository;


    @Autowired
    public AdminService(UserRepository userRepository, AdminRepository adminRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
    }

    public void deleteUserById(Long id) throws UserNotFoundException, CannotRemoveLastAdminException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException();
        }

        if(userRepository.findById(id).get().getRole() == UserRole.ADMIN.getValue() && adminRepository.countAdmins() == 1){
            throw new CannotRemoveLastAdminException();
        }
        userRepository.deleteById(id);

    }


    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(Long id) throws UserNotFoundException{
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        return userRepository.findById(id);
    }

    public void updateStudent(UserStudentDTO userStudentDTO) {
        userRepository.save(UserEntity.builder()
                .id(userStudentDTO.getId())
                .password(userRepository.findById(userStudentDTO.getId()).get().getPassword())
                .lastName(userStudentDTO.getLastName())
                .firstName(userStudentDTO.getFirstName())
                .emailFaculty(userStudentDTO.getEmailFaculty())
                .emailPersonal(userStudentDTO.getEmailPersonal())
                .build());

        studentRepository.save(StudentEntity.builder()
                .idUser(userStudentDTO.getId())
                .code(userStudentDTO.getCode())
                .year(userStudentDTO.getYear())
                .semester(userStudentDTO.getSemester())
                .build());
    }

    public List<StudentEntity> findAllStudents() {return studentRepository.findAll();}

    public UserStudentDTO findUserStudentById(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        UserEntity userEntity = userRepository.findById(id).get();
        StudentEntity studentEntity = studentRepository.findById(id).get();

        return UserStudentDTOMapper.mapToDTO(userEntity, studentEntity);
    }

}

