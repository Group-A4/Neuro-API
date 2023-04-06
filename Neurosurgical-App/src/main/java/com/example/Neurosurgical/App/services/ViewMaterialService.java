package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.MaterialNotFoundException;
import com.example.Neurosurgical.App.model.entity.MaterialEntity;
import com.example.Neurosurgical.App.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewMaterialService {
    private final MaterialRepository materialRepository;

    public ViewMaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public MaterialEntity findById(Long id){
        MaterialEntity material = materialRepository.findById(id).orElse(null);
        if(material != null)
            return material;
        else
            throw new MaterialNotFoundException();
    }

    public List<MaterialEntity> findAll(){
        List<MaterialEntity> materials = materialRepository.findAll();
        if(!materials.isEmpty())
            return materials;
        else
            throw new MaterialNotFoundException();
    }
}
