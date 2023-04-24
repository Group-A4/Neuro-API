package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.entities.Role;
import com.example.Neurosurgical.App.repositories.UserRepository;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userDao) {
        this.userRepository = userDao;

    }


    @Override
    public List<UserDto> findAll() {
        return   userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllWithRole(Integer role) {
        return userRepository.findAllWithRole(role)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException, CannotRemoveLastAdminException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException();
        }

        if(userRepository.findById(id).orElseThrow(UserNotFoundException::new).getRole() == Role.ADMIN.ordinal() && userRepository.countByRole(Role.ADMIN.ordinal()) == 1){
            throw new CannotRemoveLastAdminException();
        }
        userRepository.deleteById(id);

    }

    @Override
    public Optional<UserDto> findById(Long id) throws UserNotFoundException {
        Optional<UserEntity> user = null;
        try{
            user = userRepository.findById(id);
        }catch (Exception e){
            throw new UserNotFoundException();
        }

        return Optional.of(UserMapper.toDto(user.get()));
    }

    @Override
    public void createUser(UserEntity user) throws UserAlreadyExistsException {
        if(userRepository.findByFacMail(user.getEmailFaculty()) != null && userRepository.findByPersonalMail(user.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("email in use.");

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, UserDto userDto){
        checkIfExists(id);
        UserEntity userToBeUpdated = userRepository.findById(id).get();

        UserEntity userToUpdate = UserMapper.fromDto(userDto);
        userToUpdate.setId(id);
        userToUpdate.setRole(userToBeUpdated.getRole());
        userToUpdate.setPassword(userToBeUpdated.getPassword());
        userRepository.save(userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("User", id);
        }
    }

    @Override
    public UserDto findByFacMail(String mail) throws UserNotFoundException {
        UserEntity user = null;
        try{
            user = userRepository.findByFacMail(mail);
        }catch (Exception e){
            throw new UserNotFoundException();
        }


        return UserMapper.toDto(user);
    }
}
