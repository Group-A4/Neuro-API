package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.models.enums.UserRole;
import com.example.Neurosurgical.App.repositories.AdminRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Neurosurgical.App.models.enums.UserRole.ADMIN;

@Service
public class AdminService {


    private final UserRepository userRepository;
    private final AdminRepository adminRepository;


    @Autowired
    public AdminService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
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



}
