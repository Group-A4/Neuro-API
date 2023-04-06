package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.MaterialNotFoundException;
import com.example.Neurosurgical.App.model.entity.MaterialEntity;
import com.example.Neurosurgical.App.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public DeleteMaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * To use this method find the element that you want to delete with the method findById first
     * and then give that material object as parameter
     *
     * @param material the material object to be deleted
     */
    public void deleteMaterial(MaterialEntity material){
        materialRepository.delete(material);
    }

    public void deleteMaterialById(Long id){
        MaterialEntity material = materialRepository.findById(id).orElse(null);
        if(material != null) {
            materialRepository.delete(material);
        }
        else{
            throw new MaterialNotFoundException();
        }
    }
}

