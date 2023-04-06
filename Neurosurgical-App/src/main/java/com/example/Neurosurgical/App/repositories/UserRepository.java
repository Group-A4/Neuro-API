package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT role FROM USERS WHERE id= :id",nativeQuery = true)
    Integer findRoleById(Long id);
}
