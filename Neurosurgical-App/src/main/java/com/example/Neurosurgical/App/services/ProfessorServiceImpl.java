package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.dao.ProfessorDao;
import com.example.Neurosurgical.App.dao.UserDao;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.ProfessorMapper;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.model.dto.ProfessorCreationDto;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import com.example.Neurosurgical.App.model.dto.UserDto;
import com.example.Neurosurgical.App.model.entity.ProfessorEntity;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorDao professorDao;
    private final UserDao userDao;

    @Autowired
    public ProfessorServiceImpl(ProfessorDao professorDao, UserDao userDao) {
        this.professorDao = professorDao;
        this.userDao = userDao;
    }

    @Override
    public List<ProfessorDto> findAll() {
        List<ProfessorEntity> professorEntities = professorDao.findAll();
        List<UserEntity> userEntities = userDao.findAll();

        List<ProfessorDto> professorDtos = new ArrayList<>();

        for(var prof : professorEntities){
            UserEntity userEntity = userEntities.stream()
                    .filter(x -> Objects.equals(x.getId(), prof.getIdUser()))
                    .findAny().get();

            professorDtos.add(ProfessorMapper.toDto(userEntity, prof));
        }

        return professorDtos;
    }

    @Override
    public void deleteProfessor(Long id) {
        professorDao.deleteById(id);
        userDao.deleteById(id);
    }

    @Override
    public Optional<ProfessorDto> findById(Long id) throws UserNotFoundException {
        return Optional.of(ProfessorMapper
                .toDto(userDao.findById(id).get(), professorDao.findById(id).get()));
    }

    @Override
    public void createProfessor(ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException {
        if(userDao.findByFacMail(professorCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userDao.findByPersonalMail(professorCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(professorDao.findByCode(professorCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user = UserMapper.fromProfessorCreationDtoToUserEntity(professorCreationDto);
        userDao.save(user);

        ProfessorEntity professorEntity = ProfessorEntity.builder()
                .idUser(userDao.findByFacMail(user.getEmailFaculty()).getId())
                .code(professorCreationDto.getCode())
                .degree(professorCreationDto.getDegree())
                .build();

        professorDao.save(professorEntity);
    }

    @Override
    public void updateProfessor(Long id, ProfessorDto professorDto){
        checkIfExists(id);
        ProfessorEntity professorToUpdate = ProfessorMapper.fromDto(professorDto);
        professorToUpdate.setIdUser(id);
        professorDao.save(professorToUpdate);

        UserDto userToUpdate = UserMapper.fromProfessorDtoToUserDto(professorDto);
        new UserServiceImpl(userDao).updateUser(id, userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (professorDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Professor", id);
        }
    }


    @Override
    public Optional<ProfessorDto> findByCode(String code) throws UserNotFoundException {
        ProfessorEntity professorEntity = professorDao.findByCode(code);
        UserEntity userEntity = userDao.findById(professorEntity.getIdUser()).get();

        return Optional.of(ProfessorMapper.toDto(userEntity, professorEntity));
    }
}
