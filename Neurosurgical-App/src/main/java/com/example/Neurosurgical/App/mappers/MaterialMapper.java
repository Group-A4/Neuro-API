package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Component;

@Component

public class MaterialMapper {
    public static MaterialDto toDto(MaterialEntity materialEntity){
        return MaterialDto.builder()
                .title(materialEntity.getTitle())
                .link(materialEntity.getLink())
                .build();
    }

    public static MaterialEntity fromDto(MaterialDto materialDto){
        return MaterialEntity.builder()
                .title(materialDto.getTitle())
                .link(materialDto.getLink())
                .build();
    }
}
