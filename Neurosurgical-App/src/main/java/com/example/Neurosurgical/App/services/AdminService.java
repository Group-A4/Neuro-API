package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void deleteUserById(Long id) {
        if(!adminRepository.existsById(id)){
           // I'll add it later
        }
        adminRepository.deleteById(id);
    }



}
