package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {

    @Query(value="SELECT COUNT(*) FROM Administrators", nativeQuery = true)
    Long countAdmins();
}

