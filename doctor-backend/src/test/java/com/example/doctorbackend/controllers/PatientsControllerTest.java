package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.services.PatientsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientsService patientsService;

    @Autowired
    private ObjectMapper objectMapper;

 //   @Test
    public void findByEmailTest() throws Exception {
        Patient patient = new Patient("CR1","ismail","ismail@gmail.com","06111122111");
        Mockito.when(patientsService.getPatientByEmail(Mockito.anyString()))
                .thenReturn(patient);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/patients/email/{email}", patient.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(patient));
    }




   // @Test
    public void getAllPatientsTest() throws Exception {
        Patient patient1 = new Patient(
                "CR1",
                "patient1",
                "patient1@gmail.com",
                "06111122111"
                );
        Patient patient2 = new Patient(
                "CR2",
                "patient2",
                "patient2@gmail.com",
                "06111122111"
                );
        List<Patient> patients = List.of(patient1, patient2);
        Mockito.when(patientsService.getAllPatients()).thenReturn(patients);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertThat(result.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(patients)
                );
    }






}
