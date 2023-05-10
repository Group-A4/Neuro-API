package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.GeneralInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralInfoRepository extends JpaRepository<GeneralInfoEntity, Long> {
//    @Query(value = "SELECT * FROM general_info WHERE id = :id", nativeQuery = true)
//    Optional<GeneralInfoEntity> findById(@Param("id") Long id);
}