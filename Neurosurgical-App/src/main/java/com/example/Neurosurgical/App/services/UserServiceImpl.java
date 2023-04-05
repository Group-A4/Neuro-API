package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.dao.UserDao;
import com.example.Neurosurgical.App.exception.UserAlreadyExistsException;
import com.example.Neurosurgical.App.exception.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.model.dto.UserDto;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
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

        if(userDao.findByFacMail(user.getEmailFaculty()) != null)throw  new UserAlreadyExistsException("email in use.");
        userDao.save(user);
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
