
package com.example.Neurosurgical.App.services;


import com.example.Neurosurgical.App.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.UserStudentDTOMapper;
import com.example.Neurosurgical.App.models.dtos.UserAdminDTO;
import com.example.Neurosurgical.App.models.dtos.UserProfessorDTO;
import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.models.enums.UserRole;
import com.example.Neurosurgical.App.repositories.AdminRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;


    @Autowired
    public AdminService(UserRepository userRepository, AdminRepository adminRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
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

    public void createAccountStudent(UserStudentDTO userStudentDTO) {
        userRepository.save(UserEntity.builder()
                .password(userStudentDTO.getPassword()) // <- asta vine in request si userStudentDTO ar trebui sa contina bydeafult parola
                .lastName(userStudentDTO.getLastName())
                .firstName(userStudentDTO.getFirstName())
                .emailFaculty(userStudentDTO.getEmailFaculty())
                .emailPersonal(userStudentDTO.getEmailPersonal())
                .role(userStudentDTO.getRole())
                .build());

        studentRepository.save(StudentEntity.builder()
                .idUser(userRepository.findByFacMail(userStudentDTO.getEmailFaculty()).getId())
                .code(userStudentDTO.getCode())
                .year(userStudentDTO.getYear())
                .semester(userStudentDTO.getSemester())
                .birth_date(LocalDate.now())
                .build());

    }


    public void createAccountProfessor(UserProfessorDTO userProfessorDTO) {
        userRepository.save(UserEntity.builder()
                .lastName(userProfessorDTO.getLastName())
                .firstName(userProfessorDTO.getFirstName())
                .emailFaculty(userProfessorDTO.getEmailFaculty())
                .emailPersonal(userProfessorDTO.getEmailPersonal())
                .password(userProfessorDTO.getPassword())
                .role(userProfessorDTO.getRole())
                .build());

        professorRepository.save(ProfessorEntity.builder()
                .idUser(userRepository.findByFacMail(userProfessorDTO.getEmailFaculty()).getId())
                .code(userProfessorDTO.getCode())
                .degree(userProfessorDTO.getDegree())
                .build());
    }


    public void createAccountAdmin(UserAdminDTO userAdminDTO) {
        userRepository.save(UserEntity.builder()
                .lastName(userAdminDTO.getLastName())
                .firstName(userAdminDTO.getFirstName())
                .emailFaculty(userAdminDTO.getEmailFaculty())
                .emailPersonal(userAdminDTO.getEmailPersonal())
                .password(userAdminDTO.getPassword())
                .role(userAdminDTO.getRole())
                .build());

        adminRepository.save(AdminEntity.builder()
                .idUser(userRepository.findByFacMail(userAdminDTO.getEmailFaculty()).getId())
                .build());
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }
}
