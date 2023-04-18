package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.ProfessorMapper;
import com.example.Neurosurgical.App.mappers.UserMapper;
import com.example.Neurosurgical.App.models.dtos.ProfessorCreationDto;
import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorDao, UserRepository userDao, CourseRepository courseRepository, MaterialRepository materialRepository) {
        this.professorRepository = professorDao;
        this.userRepository = userDao;
        this.courseRepository = courseRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public List<ProfessorDto> findAll() {
        List<ProfessorEntity> professorEntities = professorRepository.findAll();
        List<UserEntity> userEntities = userRepository.findAll();

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
        checkIfExists(id);
        professorRepository.deleteById(id);
        userRepository.deleteById(id);
    }

    @Override
    public Optional<ProfessorDto> findById(Long id) throws UserNotFoundException {
        return Optional.of(ProfessorMapper
                .toDto(userRepository.findById(id).get(), professorRepository.findById(id).get()));
    }

    @Override
    public void createProfessor(ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException {
        if(userRepository.findByFacMail(professorCreationDto.getEmailFaculty()) != null)
            throw new UserAlreadyExistsException("Faculty email already in use!");
        if(userRepository.findByPersonalMail(professorCreationDto.getEmailPersonal()) != null)
            throw new UserAlreadyExistsException("Personal email already in use!");
        if(professorRepository.findByCode(professorCreationDto.getCode()) != null)
            throw new UserAlreadyExistsException("Code already in use!");

        UserEntity user = UserMapper.fromProfessorCreationDtoToUserEntity(professorCreationDto);
        userRepository.save(user);

        ProfessorEntity professorEntity = ProfessorEntity.builder()
                .idUser(userRepository.findByFacMail(user.getEmailFaculty()).getId())
                .code(professorCreationDto.getCode())
                .degree(professorCreationDto.getDegree())
                .build();

        professorRepository.save(professorEntity);
    }

    @Override
    public void updateProfessor(Long id, ProfessorDto professorDto){
        checkIfExists(id);
        ProfessorEntity professorToUpdate = ProfessorMapper.fromDto(professorDto);
        professorToUpdate.setIdUser(id);
        professorRepository.save(professorToUpdate);

        UserDto userToUpdate = UserMapper.fromProfessorDtoToUserDto(professorDto);
        new UserServiceImpl(userRepository).updateUser(id, userToUpdate);
    }

    public void checkIfExists(Long id) {
        if (professorRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Professor", id);
        }
    }


    @Override
    public Optional<ProfessorDto> findByCode(String code) throws UserNotFoundException {
        ProfessorEntity professorEntity = professorRepository.findByCode(code);
        UserEntity userEntity = userRepository.findById(professorEntity.getIdUser()).get();

        return Optional.of(ProfessorMapper.toDto(userEntity, professorEntity));
    }

    @Override
    public List<ProfessorDto> findByCourseId(Long id) throws UserNotFoundException {
        CourseEntity courseEntity = courseRepository.findById(id).get();
        List<ProfessorEntity> professors = courseEntity.getTeachings().stream().map(DidacticEntity::getProfessor).toList();
        List<UserEntity> users = userRepository.findAll();

        List<ProfessorDto> professorDtos = new ArrayList<>();
        for(var prof : professors){
            UserEntity userEntity = users.stream()
                    .filter(x -> Objects.equals(x.getId(), prof.getIdUser()))
                    .findAny().get();
            professorDtos.add(ProfessorMapper.toDto(userEntity, prof));
        }

        return professorDtos;
    }

    @Override
    public Optional<ProfessorDto> findByMaterialId(Long id) throws UserNotFoundException{
        MaterialEntity materialEntity = materialRepository.findById(id).get();
        ProfessorEntity professorEntity = materialEntity.getProfessor();
        UserEntity userEntity = userRepository.findById(professorEntity.getIdUser()).get();

        return Optional.of(ProfessorMapper.toDto(userEntity, professorEntity));
    }
}
