package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = "application/json")
    public List<UserDto> getAll(){
        return userService.findAll();
    }

    @GetMapping(value = "role/{role}", produces = "application/json")
    public List<UserDto> getAllWithRole(@PathVariable @Valid @Min(0) Integer role){
        return userService.findAllWithRole(role);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<UserDto> getById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        return userService.findById(id);
    }
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException, CannotRemoveLastAdminException
    {
        userService.deleteUser(id);
    }
    @PostMapping(value = "/create", produces = "application/json")
    public void createUser(@RequestBody @Valid UserEntity user) throws UserAlreadyExistsException {
        userService.createUser(user);
    }

    @GetMapping(value = "/mail/{mail}", produces = "application/json")
    public UserDto getByMail(@PathVariable @Valid String mail) throws UserNotFoundException {
        return userService.findByFacMail(mail);
    }
    @PutMapping("update/{id}")
    public void updateUser(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid UserDto userDto) {
        userService.updateUser(id, userDto);
    }

}
