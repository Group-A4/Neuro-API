package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Component;

@Component

public class MaterialMapper {
    public static MaterialDto toDto(MaterialEntity materialEntity, String html){
        return MaterialDto.builder()
                .id(materialEntity.getId())
                .title(materialEntity.getTitle())
                .html(html)
                .build();
    }

//    public static MaterialEntity fromDto(MaterialDto materialDto){
//        return MaterialEntity.builder()
//                .title(materialDto.getTitle())
//                .build();
//    }
}
