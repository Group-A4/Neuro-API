package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnListOfUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllWithRole_shouldReturnListOfUsersWithRole() throws Exception {
        Integer role = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/users/role/{role}", role))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_shouldReturnUserById_shouldReturnOk() throws Exception {
        Long id = 60L;
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_shouldReturnUserById_shouldReturnBadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void deleteUserById_shouldReturnOk() throws Exception {
        Long id = 60L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUserById_shouldReturnNotFound() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createUser_shouldReturnBadRequest() throws Exception {
        UserEntity userCreation = UserEntity.builder()
                .password("passWord")
                .lastName("Phillip")
                .firstName("Graves")
                .emailFaculty("cstilled@umfiasi.ro")
                .emailPersonal("cstilled@xing.com")
                .role(1)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .content(objectMapper.writeValueAsString(userCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @Rollback
    void createUser_shouldReturnOk() throws Exception {
        UserEntity userCreation = UserEntity.builder()
                .password("passWord")
                .lastName("Phillip")
                .firstName("Graves")
                .emailFaculty("graves@gmail.com")
                .emailPersonal("graves@gmail.com")
                .role(1)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .content(objectMapper.writeValueAsString(userCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByMail_shouldReturnUserByMail_shouldReturnOk() throws Exception {
        String mail = "cstilled@umfiasi.ro";
        mockMvc.perform(MockMvcRequestBuilders.get("/users/mail/{mail}", mail))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByMail_shouldReturnUserByMail_shouldReturnBadRequest() throws Exception {
        String mail = "aaaaaaaaa@umfiasi.ro";
        mockMvc.perform(MockMvcRequestBuilders.get("/users/mail/{mail}", mail))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateUser_shouldReturnNotFound() throws Exception {
        Long id = 1000L;
        UserEntity userCreation = UserEntity.builder()
                .password("password123")
                .lastName("Frei")
                .firstName("Peter")
                .emailFaculty("freipeter@gmail.com")
                .emailPersonal("freipeter@gmail.com")
                .role(0)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/{id}", id)
                        .content(objectMapper.writeValueAsString(userCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Rollback
    void updateUser_shouldReturnOk() throws Exception {
        Long id =60L;
        UserEntity userCreation = UserEntity.builder()
                .password("password123")
                .lastName("Frei")
                .firstName("Peter")
                .emailFaculty("freipeter@gmail.com")
                .emailPersonal("freipeter@gmail.com")
                .role(1)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/{id}", id)
                        .content(objectMapper.writeValueAsString(userCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
