package com.example.Neurosurgical.App.repository;

import com.example.Neurosurgical.App.model.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
    //
}
