package com.example.candidatemanagement.test;


import com.example.candidatemanagement.api.repository.CandidateRepository;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateRepository candidateRepository;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private DirectionRepository directionRepository;

    @Test
    public void getAllCandidatesTest() throws Exception {
        mockMvc.perform(get("/api/v1/candidate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createCandidateTest() throws Exception {
        mockMvc.perform(post("/api/v1/candidate")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("middleName", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCandidateTest() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(put("/api/v1/candidate/" + id.toString())
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("middleName", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

