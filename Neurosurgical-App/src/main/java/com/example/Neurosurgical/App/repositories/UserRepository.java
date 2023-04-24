package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM USERS WHERE email_faculty = :mail", nativeQuery = true)
    UserEntity findByFacMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM USERS WHERE email_personal = :mail", nativeQuery = true)
    UserEntity findByPersonalMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM USERS WHERE role = :p_role", nativeQuery = true)
    List<UserEntity> findAllWithRole(@Param("p_role") Integer role);

    @Query(value = "SELECT COUNT(*) FROM USERS WHERE ROLE = :p_role", nativeQuery = true)
    Long countByRole(@Param("p_role") Integer role);
}
