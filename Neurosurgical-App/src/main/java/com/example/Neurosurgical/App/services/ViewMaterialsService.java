package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.repositories.ViewMaterialsRepository;
import org.springframework.stereotype.Service;
@Service
public class ViewMaterialsService {
    private final ViewMaterialsRepository viewMaterialsRepository;
    public ViewMaterialsService(ViewMaterialsRepository viewMaterialsRepository)
    {
        this.viewMaterialsRepository=viewMaterialsRepository;
    }
    public MaterialEntity findByID(Long id)
    {
        return viewMaterialsRepository.findById(id);
    }
    public List<MaterialEntity> findAll()
    {
        return viewMaterialsRepository.findAll();
    }
}
