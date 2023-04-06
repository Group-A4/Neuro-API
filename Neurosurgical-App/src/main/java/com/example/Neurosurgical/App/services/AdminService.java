package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.dtos.UserAdminDTO;
import com.example.Neurosurgical.App.models.dtos.UserProfessorDTO;
import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.Admin;
import com.example.Neurosurgical.App.models.entities.Professor;
import com.example.Neurosurgical.App.models.entities.Student;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.repositories.AdminRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;

import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;

    public AdminService(UserRepository userRepository1, StudentRepository studentRepository, ProfessorRepository professorRepository, AdminRepository adminRepository){
        this.userRepository = userRepository1;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.adminRepository = adminRepository;
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)){
           // I'll add it later
        }
        userRepository.deleteById(id);
    }

    public List<UserEntity> findAll(){
        return userRepository.findalll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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

        //System.out.println(userRepository.getPassword(userStudentDTO.getId()));
        studentRepository.save(Student.builder()
                .id_users(userRepository.findByFacMail(userStudentDTO.getEmailFaculty()).getId())
                .code(userStudentDTO.getCode())
                .year(userStudentDTO.getYear())
                .semester(userStudentDTO.getSemester())
                .birthDate(LocalDate.now())
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

        professorRepository.save(Professor.builder()
                .id_users(userRepository.findByFacMail(userProfessorDTO.getEmailFaculty()).getId())
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

        adminRepository.save(Admin.builder()
                .id_users(userRepository.findByFacMail(userAdminDTO.getEmailFaculty()).getId())
                .build());
    }
}
