package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.repositories.ViewMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewMaterialsService {
    @Autowired
    private final ViewMaterialsRepository viewMaterialsRepository;
    public ViewMaterialsService(ViewMaterialsRepository viewMaterialsRepository)
    {
        this.viewMaterialsRepository=viewMaterialsRepository;
    }
    public MaterialEntity findByID(Long id)
    {
        return viewMaterialsRepository.findById(id).get();
    }
    public List<MaterialEntity> findAll()
    {
        return viewMaterialsRepository.findAll();
    }
}
