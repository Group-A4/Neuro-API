package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.GeneralInfoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GeneralInfoService {
    Optional<GeneralInfoDto> findById(Long id) throws EntityNotFoundException;
    void updateGeneralInfo(Long id, GeneralInfoDto generalInfoDto);
}
