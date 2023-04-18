package com.example.Neurosurgical.App.controllers;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StudentFollowsCoursesControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteStudentFollowsCourseById_shouldReturnNotFound() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourses/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteStudentFollowsCourseByCourseIdAndStudentId_shouldReturnNotFound() throws Exception {
        Long courseId = 1000L;
        Long studentId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourses/delete/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createStudentFollowsCourse_shouldReturnNotFound() throws Exception {
        Long courseId = 10000L;
        Long studentId = 10000L;

        mockMvc.perform(MockMvcRequestBuilders.post("/studentFollowsCourses/create/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Rollback
    void deleteStudentFollowsCourseById_shouldReturnOk() throws Exception {
        Long id = 12L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourses/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Rollback
    void deleteStudentFollowsCourseByCourseIdAndStudentId_shouldReturnOk() throws Exception {
        Long courseId = 3L;
        Long studentId = 49L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourses/delete/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void createStudentFollowsCourse_shouldReturnOk() throws Exception {
        Long courseId = 4L;
        Long studentId = 51L;

        mockMvc.perform(MockMvcRequestBuilders.post("/studentFollowsCourses/create/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
