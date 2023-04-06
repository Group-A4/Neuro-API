package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.model.entity.MaterialEntity;
import com.example.Neurosurgical.App.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadMaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public UploadMaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void addMaterial(MaterialEntity material){
        materialRepository.save(material);
    }
}
