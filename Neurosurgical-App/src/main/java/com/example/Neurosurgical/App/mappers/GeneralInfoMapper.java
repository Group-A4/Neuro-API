package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.GeneralInfoDto;
import com.example.Neurosurgical.App.models.entities.GeneralInfoEntity;
import org.springframework.stereotype.Component;

@Component
public class GeneralInfoMapper {
    public static GeneralInfoDto toDto(GeneralInfoEntity generalInfoEntity) {
        return GeneralInfoDto.builder().
                quizTime(generalInfoEntity.getQuizTime())
                .build();
    }

    public static GeneralInfoEntity fromDto(GeneralInfoDto generalInfoDto) {
        return GeneralInfoEntity.builder()
                .quizTime(generalInfoDto.getQuizTime())
                .build();
    }
}
