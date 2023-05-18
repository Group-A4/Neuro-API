package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.models.entities.MaterialsMarkdownEntity;
import org.springframework.stereotype.Component;

@Component

public class MaterialMapper {
    public static MaterialDto toDto(MaterialEntity materialEntity, String markdownText, String html){
        return MaterialDto.builder()
                .id(materialEntity.getId())
                .title(materialEntity.getTitle())
                .markdownText(markdownText)
                .html(html)
                .build();
    }

    public static MaterialEntity fromDto(MaterialDto materialDto){
        return MaterialEntity.builder()
                .title(materialDto.getTitle())
                .build();
    }
}
