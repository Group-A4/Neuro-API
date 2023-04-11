package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.dao.UserDao;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.ProfessorMapper;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.model.dto.UserDto;
import com.example.Neurosurgical.App.model.entity.ProfessorEntity;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> findAll() {
        return   userDao.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllWithRole(Integer role) {
        return userDao.findAllWithRole(role)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Optional<UserDto> findById(Long id) throws UserNotFoundException {
        Optional<UserEntity> user = null;
        try{
            user = userDao.findById(id);
        }catch (Exception e){
            throw new UserNotFoundException();
        }

        return Optional.of(UserMapper.toDto(user.get()));
    }

    @Override
    public void createUser(UserEntity user) throws UserAlreadyExistsException {
        if(userDao.findByFacMail(user.getEmailFaculty()) != null && userDao.findByPersonalMail(user.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("email in use.");

        userDao.save(user);
    }

    @Override
    public void updateUser(Long id, UserDto userDto){
        checkIfExists(id);
        UserEntity userToBeUpdated = userDao.findById(id).get();

        UserEntity userToUpdate = UserMapper.fromDto(userDto);
        userToUpdate.setId(id);
        userToUpdate.setRole(userToBeUpdated.getRole());
        userToUpdate.setPassword(userToBeUpdated.getPassword());
        userDao.save(userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (userDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("User", id);
        }
    }

    @Override
    public UserDto findByFacMail(String mail) throws UserNotFoundException {
        UserEntity user = null;
        try{
            user = userDao.findByFacMail(mail);
        }catch (Exception e){
            throw new UserNotFoundException();
        }


        return UserMapper.toDto(user);
    }
}
