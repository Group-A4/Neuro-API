package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.recovery.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Long> {

    @Query(value = "SELECT * FROM RECOVERY_CODE WHERE email_faculty = :mail", nativeQuery = true)
    Recovery findByFacMail(@Param("mail") String mail);
}
