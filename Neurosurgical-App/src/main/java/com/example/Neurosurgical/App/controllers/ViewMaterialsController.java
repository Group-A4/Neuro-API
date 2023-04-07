package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.services.ViewMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class ViewMaterialsController {


    private final ViewMaterialsService viewMaterialsService;

    @Autowired
    public ViewMaterialsController(ViewMaterialsService viewMaterialsService)
    {
        this.viewMaterialsService = viewMaterialsService;
    }
    @GetMapping(value = "/materials" ,produces = "application/json")
    public List<MaterialEntity> getAll(){

        return viewMaterialsService.findAll();
    }

    @GetMapping(value = "/materials/{id}" ,produces = "application/json")
    public MaterialEntity findByID(@PathVariable Long id)
    {
        return viewMaterialsService.findByID(id);
    }

}
