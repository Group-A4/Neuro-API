package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.GeneralInfoMapper;
import com.example.Neurosurgical.App.models.dtos.GeneralInfoDto;
import com.example.Neurosurgical.App.models.entities.GeneralInfoEntity;
import com.example.Neurosurgical.App.repositories.GeneralInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneralInfoServiceImpl implements GeneralInfoService{
    final private GeneralInfoRepository generalInfoRepository;

    @Autowired
    public GeneralInfoServiceImpl(GeneralInfoRepository generalInfoRepository) {
        this.generalInfoRepository = generalInfoRepository;
    }

    @Override
    public Optional<GeneralInfoDto> findById(Long id) throws EntityNotFoundException {
        Optional<GeneralInfoEntity> generalInfoEntity = this.generalInfoRepository.findById(id);

        if (generalInfoEntity.isEmpty()) {
            throw new EntityNotFoundException("GeneralInfo", id.toString());
        }
        return Optional.of(GeneralInfoMapper.toDto(generalInfoEntity.get()));
    }

    public void checkIfExists(Long id) {
        if (generalInfoRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("GeneralInfo", id);
        }
    }

    @Override
    public void updateGeneralInfo(Long id, GeneralInfoDto generalInfoDto) {
        checkIfExists(id);
        GeneralInfoEntity generalInfoToUpdate = GeneralInfoMapper.fromDto(generalInfoDto);
        generalInfoToUpdate.setId(id);
        generalInfoRepository.save(generalInfoToUpdate);
    }
}
