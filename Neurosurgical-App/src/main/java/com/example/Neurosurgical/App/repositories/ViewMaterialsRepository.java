package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewMaterialsRepository extends JpaRepository<MaterialEntity, Long>{

    @Query(value = "SELECT * FROM Materials",nativeQuery = true)
    List<MaterialEntity> findAll();

}
