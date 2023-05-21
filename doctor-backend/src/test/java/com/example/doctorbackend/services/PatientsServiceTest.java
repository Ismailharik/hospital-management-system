package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.PatientDTO;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.enums.Role;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
import com.example.doctorbackend.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientsServiceTest {

    private PatientsService patientsService;
    private  Mapper mapper;
    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void SetUp(){
        mapper=new Mapper();
        patientsService = new PatientsServiceImpl(patientRepository,mapper);
    }

    @Test
    public void givenNoPatients_whenGetAllPatients_thenReturnEmptyList() {
        // Given: No patients in the repository
        // When: Calling getAllPatients
        List<PatientDTO> patients = patientsService.getAllPatients();

        // Then: Verify that an empty list of patients is returned
        assertThat(patients).isEmpty();
    }
    @Test
    public void givenPatientEmail_whenGetPatientByEmail_thenPatientIsReturned() {
        // Given
        Patient patient = createSamplePatient();

        given(patientRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(patient));

        // When
        PatientDTO patientDTO = patientsService.getPatientByEmail("patient1@gmail.com");

        // Then
        assertThat(patientDTO.getEmail()).isEqualTo(patient.getEmail());
        assertThat(patientDTO.getImage()).isEqualTo(patient.getImage());
        assertThat(patientDTO.getFirstname()).isEqualTo(patient.getFirstname());
        assertThat(patientDTO.getLastname()).isEqualTo(patient.getLastname());
    }
    @Test
    void givenInvalidEmail_whenGetPatientById_thenThrowConflictException(){
        // Given
        String email = "invalid_email";
        given(patientRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> patientsService.getPatientByEmail(email)).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining(String.format("Patient not found with email : '%s'", email));
    }


    @Test
    public void givenPatientId_whenGetPatientById_thenPatientIsReturned() {
        // Given
        Patient patient = createSamplePatient();

        given(patientRepository.findById(anyString())).willReturn(Optional.ofNullable(patient));

        // When
        PatientDTO patientDTO = patientsService.getPatientById("CR1");

        // Then
        assertThat(patientDTO.getId()).isEqualTo(patient.getId());
        assertThat(patientDTO.getEmail()).isEqualTo(patient.getEmail());
        assertThat(patientDTO.getImage()).isEqualTo(patient.getImage());
        assertThat(patientDTO.getFirstname()).isEqualTo(patient.getFirstname());
        assertThat(patientDTO.getLastname()).isEqualTo(patient.getLastname());
    }
    @Test
    public void givenInvalidPatientId_whenGetPatientById_thenThrowNotFoundException() {
        // Given
        String email = "invalid_email";
        given(patientRepository.findById(anyString())).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> patientsService.getPatientById(email)).
                isInstanceOf(NotFoundException.class).
                hasMessageContaining(String.format("Patient not found with id : '%s'", email));
    }

    @Test
    public void givenValidPatient_whenSavePatient_thenPatientIsSaved() {
        // Given
        Patient patient = createSamplePatient();

        // When
        patientsService.addPatient(patient);
        // Then
        ArgumentCaptor<Patient> patientArgumentCaptor=ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient capturedPatient = patientArgumentCaptor.getValue();
        assertThat(capturedPatient).isEqualTo(patient);
    }


    @Test
    public void givenValidPatient_whenDeletePatient_thenPatientDeleted() throws NotFoundException {
        // Given
        Patient patient = createSamplePatient();

        //when
        given(patientRepository.findById(anyString())).willReturn(Optional.ofNullable(patient));
        // Call the deletePatient method with the patient's ID
        patientsService.deletePatient(patient.getId());

        // Verify that the findById method of the mock object is called once with the patient's ID
        verify(patientRepository, times(1)).findById(patient.getId());

        // Verify that the delete method of the mock object is called once with the patient object
        verify(patientRepository, times(1)).delete(patient);
    }
    @Test
    public void givenValidPatient_whenAddPatient_thenPatientIsSaved() {
        // Given
        Patient patient = createSamplePatient();

        //When
        given(patientRepository.save(patient)).willReturn(patient);

        //Then
        Patient addedPatient = patientsService.addPatient(patient);
        verify(patientRepository, times(1)).save(patient);
        assertThat(addedPatient).isNotNull();
        assertThat(addedPatient.getId()).isEqualTo(patient.getId());
    }
    @Test
    public void givenInvalidEmail_whenAddPatient_thenThrowConflictException() {
        // Given
        Patient patient = createSamplePatient();
        given(patientRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(patient));

        // When and Then
        assertThatThrownBy(() -> patientsService.addPatient(patient))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Email already exist");
        verify(patientRepository, never()).save(any());
    }

    @Test
    public void givenPatient_whenUpdatePatient_thenReturnPatient() {
        // Given
        Patient patient = createSamplePatient();
        PatientDTO patientDTO = createSamplePatientDTO();


        //When
        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));
        given(patientRepository.save(patient)).willReturn(patient);
        PatientDTO updatedPatient = patientsService.updatePatient(patient.getId(),patientDTO);

        //Then
        verify(patientRepository, times(1)).save(patient);
        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getId()).isEqualTo(patient.getId());
    }
    @Test
    public void givenNewPatientAndImage_whenAddPatient_thenPatientIsSavedWithImage() throws IOException {
        // Given
        Patient patient = createSamplePatient();
        MockMultipartFile imageFile = createSampleImageFile();

        given(patientRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // When
        Patient savedPatient = patientsService.addPatient(patient, imageFile);

        // Then
        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(patientArgumentCaptor.capture());

        Patient capturedPatient = patientArgumentCaptor.getValue();
//        assertThat(capturedPatient).isEqualTo(savedPatient);
        assertThat(capturedPatient.getImage()).isNotNull();
        assertThat(capturedPatient.getImage()).endsWith(".jpg");
        assertThat(Files.exists(Paths.get(capturedPatient.getImage()))).isTrue();
    }
    @Test
    public void givenExistingPatientEmail_whenAddPatient_thenThrowConflictException() throws IOException {
        // Given
        Patient patient = createSamplePatient();
        MockMultipartFile imageFile = createSampleImageFile();
        given(patientRepository.findByEmail(anyString())).willReturn(Optional.of(patient));

        // When and Then
        assertThatThrownBy(() -> patientsService.addPatient(patient, imageFile))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Email already exist");

        verify(patientRepository).findByEmail(patient.getEmail());
    }









    // Helper methods for creating sample Patient and PatientDTO objects
    private Patient createSamplePatient() {
        Patient patient = new Patient();
        patient.setId("CR1");
        patient.setImage("/path/to/image.png");
        patient.setEmail("tst@gmail.com");
        patient.setPhone("04958438");
        patient.setFirstname("test");
        patient.setLastname("test");
        patient.setRole(Role.USER);
        return patient;
    }

    private PatientDTO createSamplePatientDTO() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId("CR1");
        patientDTO.setImage("/path/to/image.png");
        patientDTO.setEmail("tst@gmail.com");
        patientDTO.setPhone("04958438");
        patientDTO.setFirstname("test");
        patientDTO.setLastname("test");
        patientDTO.setRole(Role.USER);
        return patientDTO;
    }
    private MockMultipartFile createSampleImageFile() throws IOException {
        byte[] imageData = new byte[]{/* image data */};
        return new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", imageData);
    }
}
