package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UserDto> findAll();
    List<UserDto> findAllWithRole(Integer role);
    void deleteUser(Long id);
    Optional<UserDto> findById(Long id) throws UserNotFoundException;
    void createUser(UserEntity user) throws UserAlreadyExistsException;
    void updateUser(Long id, UserDto userDto);
    UserDto findByFacMail(String mail) throws UserNotFoundException;
}
