package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.services.DoctorsService;
import com.example.doctorbackend.services.PatientsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorsService doctorsService;

    @Autowired
    private ObjectMapper objectMapper;

   // @Test
    public void createControllerTest() throws Exception {

        Doctor doctor = new Doctor();
        doctor.setEmail("doctortest@gmail.com");
        doctor.setFirstname("doctortest");
        doctor.setComment("comment");

        Mockito.when(doctorsService.createDoctor(Mockito.any(Doctor.class),Mockito.any())).thenReturn(doctor);

        mockMvc.perform(
                        post("/api/v1/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(doctor))
                )
                .andExpect(status().isCreated());
    }

}
