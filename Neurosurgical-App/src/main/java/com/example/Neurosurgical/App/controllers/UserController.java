package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.ErrorResponse;
import com.example.Neurosurgical.App.advice.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(){
        return userService.findAll();
    }

    @GetMapping(value = "role/{role}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllWithRole(@PathVariable @Valid @Min(0) Integer role){
        return userService.findAllWithRole(role);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<UserDto> getById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        Optional<UserDto> userDto = userService.findById(id);
        if(userDto.isPresent()){
            return userDto;
        } else {
            throw new UserNotFoundException();
        }
    }
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException, CannotRemoveLastAdminException {
        userService.deleteUser(id);
    }
    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserEntity user) throws UserAlreadyExistsException {
        userService.createUser(user);
    }

    @GetMapping(value = "/mail/{mail}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getByMail(@PathVariable @Valid String mail) throws UserNotFoundException {
        UserDto userDto = userService.findByFacMail(mail);
        if(userDto != null){
            return userDto;
        } else {
            throw new UserNotFoundException();
        }
    }
    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex)
    {
        return new ErrorResponse(ex.getMessage());
    }

}
