//package com.example.Neurosurgical.App.dao;
//
//import com.example.Neurosurgical.App.model.entity.StudentEntity;
//import com.example.Neurosurgical.App.model.entity.UserEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface StudentDao extends JpaRepository<StudentEntity,Long> {
//
//    @Query(value = "SELECT id_user FROM student WHERE id := p_id",nativeQuery = true)
//    Long findUserByFK(Long p_id);
//}
