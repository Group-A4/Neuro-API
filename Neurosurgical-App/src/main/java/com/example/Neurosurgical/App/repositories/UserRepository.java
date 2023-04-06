package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM USERS WHERE email_faculty = :mail",nativeQuery = true)
    UserEntity findByFacMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM USERS WHERE email_personal = :mail",nativeQuery = true)
    UserEntity findByPersonalMail(@Param("mail") String mail);
}
