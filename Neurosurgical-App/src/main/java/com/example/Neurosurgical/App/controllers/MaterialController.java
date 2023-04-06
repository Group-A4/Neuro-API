package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.MaterialNotFoundException;
import com.example.Neurosurgical.App.model.entity.MaterialEntity;
import com.example.Neurosurgical.App.services.DeleteMaterialService;
import com.example.Neurosurgical.App.services.UploadMaterialService;
import com.example.Neurosurgical.App.services.ViewMaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    private final DeleteMaterialService deleteMaterialService;
    private final UploadMaterialService uploadMaterialService;
    private final ViewMaterialService viewMaterialService;

    @Autowired
    public MaterialController(DeleteMaterialService deleteMaterialService,
                              UploadMaterialService uploadMaterialService,
                              ViewMaterialService viewMaterialService) {
        this.deleteMaterialService = deleteMaterialService;
        this.uploadMaterialService = uploadMaterialService;
        this.viewMaterialService = viewMaterialService;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteMaerialById(@PathVariable Long id) throws MaterialNotFoundException {
        deleteMaterialService.deleteMaterialById(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void createMaterial(@RequestBody MaterialEntity material){
        uploadMaterialService.addMaterial(material);
    }

    @GetMapping(value = "", produces = "application/json")
    public List<MaterialEntity> getAll() throws MaterialNotFoundException {
        return viewMaterialService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public MaterialEntity getById(@Valid @Min(0) @PathVariable Long id) throws MaterialNotFoundException {
        return viewMaterialService.findById(id);
    }
}
