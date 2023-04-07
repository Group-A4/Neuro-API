package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewMaterialsRepository extends JpaRepository<MaterialEntity, Long>{
}
