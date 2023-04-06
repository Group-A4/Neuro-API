package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.dao.ProfessorDao;
import com.example.Neurosurgical.App.dao.UserDao;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.ProfessorMapper;
import com.example.Neurosurgical.App.model.dto.ProfessorCreationDto;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
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
            ProfessorDto professorDto = ProfessorMapper.toDto(prof);
            UserEntity userEntity = userEntities.stream()
                    .filter(x -> Objects.equals(x.getId(), prof.getIdUser()))
                    .findAny().get();

            professorDto.setEmailFaculty(userEntity.getEmailFaculty());
            professorDto.setEmailPersonal(userEntity.getEmailPersonal());
            professorDto.setFirstName(userEntity.getFirstName());
            professorDto.setLastName(userEntity.getLastName());
            professorDtos.add(professorDto);
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
        ProfessorDto professorDto = ProfessorMapper.toDto(professorDao.findById(id).get());
        UserEntity userEntity = userDao.findById(id).get();

        professorDto.setEmailFaculty(userEntity.getEmailFaculty());
        professorDto.setEmailPersonal(userEntity.getEmailPersonal());
        professorDto.setFirstName(userEntity.getFirstName());
        professorDto.setLastName(userEntity.getLastName());

        return Optional.of(professorDto);
    }

    @Override
    public void createProfessor(ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException {
        if(userDao.findByFacMail(professorCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userDao.findByPersonalMail(professorCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(professorDao.findByCode(professorCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user = UserEntity.builder()
                .firstName(professorCreationDto.getFirstName())
                .lastName(professorCreationDto.getLastName())
                .emailFaculty(professorCreationDto.getEmailFaculty())
                .emailPersonal(professorCreationDto.getEmailPersonal())
                .password(professorCreationDto.getPassword())
                .role(1)
                .build();

        userDao.save(user);

        ProfessorEntity professorEntity = ProfessorEntity.builder()
                .idUser(userDao.findByFacMail(user.getEmailFaculty()).getId())
                .code(professorCreationDto.getCode())
                .degree(professorCreationDto.getDegree())
                .build();

        professorDao.save(professorEntity);
    }

    @Override
    public Optional<ProfessorDto> findByCode(String code) throws UserNotFoundException {
        ProfessorEntity professorEntity = professorDao.findByCode(code);
        UserEntity userEntity = userDao.findById(professorEntity.getIdUser()).get();

        ProfessorDto professorDto = ProfessorMapper.toDto(professorEntity);
        professorDto.setFirstName(userEntity.getFirstName());
        professorDto.setLastName(userEntity.getLastName());
        professorDto.setEmailFaculty(userEntity.getEmailFaculty());
        professorDto.setEmailPersonal(userEntity.getEmailPersonal());

        return Optional.of(professorDto);
    }
}
