package com.example.Neurosurgical.App.dao;

import com.example.Neurosurgical.App.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM USERS WHERE email_faculta := mail",nativeQuery = true)
    UserEntity findByFacMail(String mail);

}
