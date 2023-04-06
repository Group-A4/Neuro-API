package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.services.ViewMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ViewMaterialsController {
    @Autowired
    private final ViewMaterialsService viewMaterialsService;
    public ViewMaterialsController(ViewMaterialsService viewMaterialsService)
    {
        this.viewMaterialsService = viewMaterialsService;
    }
    public MaterialEnity findByID(Long id)
    {
        viewMaterialsService.findByID(id);
    }

}
