package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserEntity> findAll();
}
