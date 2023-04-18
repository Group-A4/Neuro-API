package com.example.Neurosurgical.App.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentFollowsCoursesControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteStudentFollowsCourseById_shouldReturnBadRequest() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourse/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteStudentFollowsCourseByCourseIdAndStudentId_shouldReturnBadRequest() throws Exception {
        Long courseId = 1000L;
        Long studentId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourse/delete/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createStudentFollowsCourse_shouldReturnBadRequest() throws Exception {
        Long courseId = 2L;
        Long studentId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.post("/studentFollowsCourse/create/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void deleteStudentFollowsCourseByCourseIdAndStudentId_shouldReturnOk() throws Exception {
        Long courseId = 3L;
        Long studentId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/studentFollowsCourse/delete/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createStudentFollowsCourse_shouldReturnOk() throws Exception {
        Long courseId = 3L;
        Long studentId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.post("/studentFollowsCourse/create/course={courseId}/student={studentId}", courseId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
