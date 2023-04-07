package com.example.Neurosurgical.App.dao;

import com.example.Neurosurgical.App.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long> {

    @Query(value = "SELECT * FROM USERS WHERE email_faculty = :mail",nativeQuery = true)
    UserEntity findByFacMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM USERS WHERE email_personal = :mail",nativeQuery = true)
    UserEntity findByPersonalMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM USERS WHERE role = :p_role",nativeQuery = true)
    List<UserEntity> findAllWithRole(@Param("p_role") Integer role);
}
