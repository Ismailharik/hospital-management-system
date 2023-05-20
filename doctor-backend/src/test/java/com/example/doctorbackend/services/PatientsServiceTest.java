//package com.example.doctorbackend.services;
//
//import com.example.doctorbackend.entities.Patient;
//import com.example.doctorbackend.error.NotFoundException;
//import com.example.doctorbackend.repositories.PatientRepository;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//@SpringBootTest
//public class PatientsServiceTest {
//    @Autowired
//    private PatientsService patientsService;
//    @MockBean
//    private PatientRepository patientRepository;
//
//    @Test
//    public void givenValidPatient_whenSavePatient_thenPatientIsSaved() {
//        // Given
//        Patient patient = new Patient("CR1", "patient1", "patient1@gmail.com", "06111122111");
//        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
//
//        // When
//        Patient savedPatient = patientsService.addPatient(patient);
//
//        // Then
//        assertThat(savedPatient).isNotNull();
//        assertThat(savedPatient.getEmail()).isEqualTo(patient.getEmail());
//    }
//
//    @Test
//    public void givenPatientEmail_whenGetPatientByEmail_thenPatientIsReturned() {
//        // Given
//        Patient patient = new Patient("CR1", "patient1", "patient1@gmail.com", "06111122111");
//        given(patientRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(patient));
//
//        // When
//        Patient result = patientsService.getPatientByEmail("patient1@gmail.com");
//
//        // Then
//        assertThat(result.getEmail()).isEqualTo(patient.getEmail());
//        assertThat(result.getName()).isEqualTo(patient.getName());
//    }
//
//    @Test
//    public void givenPatientId_whenGetPatientById_thenPatientIsReturned() {
//        // Given
//        Patient patient = new Patient("CR1", "patient1", "patient1@gmail.com", "06111122111");
//        given(patientRepository.findById(anyString())).willReturn(Optional.ofNullable(patient));
//
//        // When
//        Patient result = patientsService.getPatientById("CR1");
//
//        // Then
//        assertThat(result.getEmail()).isEqualTo(patient.getEmail());
//        assertThat(result.getName()).isEqualTo(patient.getName());
//    }
//
//    @Test
//    public void givenInvalidPatientEmail_whenGetPatientByEmail_thenThrowNotFoundException() {
//        // Given
//        String email = "invalid_email";
//        given(patientRepository.findByEmail(anyString())).willReturn(Optional.empty());
//
//        // When and Then
//        assertThatThrownBy(() -> patientsService.
//                getPatientByEmail(email)).
//                isInstanceOf(NotFoundException.class).
//                hasMessageContaining(String.format("Patient not found with email : '%s'", email));
//    }
//
//    @Test
//    public void givenInvalidPatientId_whenGetPatientById_thenThrowNotFoundException() {
//        // Given
//        String email = "invalid_email";
//        given(patientRepository.findByEmail(anyString())).willReturn(Optional.empty());
//
//        // When and Then
//        assertThatThrownBy(() -> patientsService.getPatientById(email)).
//                isInstanceOf(NotFoundException.class).
//                hasMessageContaining(String.format("Patient not found with id : '%s'", email));
//    }
//
//    @Test
//    public void whenFindAll_ReturnPatientsList() {
//        Patient patient1 = new Patient("CR1",
//                "patient1",
//                "patient1@gmail.com",
//                "06111122111"
//                );
//        Patient patient2 = new Patient("CR2",
//                "patient2",
//                "patient2@gmail.com",
//                "06111122111"
//                );
//        List<Patient> patients = List.of(patient1, patient2);
//
//        given(patientRepository.findAll()).willReturn(patients);
//
//        assertThat(patientsService.getAllPatients()).hasSize(2).contains(patient1, patient2);
//    }
//
//    @Test
//    void whenGetById_PatientShouldBeFound() {
//        Patient patient = new Patient("CR1"
//                , "patient1",
//                "patient1@gmail.com",
//                "06111122111"
//                );
//        given(patientRepository.findById(anyString())).willReturn(Optional.ofNullable(patient));
//        Patient result = patientsService.getPatientById("CR1");
//        assertThat(result.getEmail().equals(patient.getEmail()));
//        assertThat(result.getName().equals(patient.getName()));
//    }
//
//    @Test
//    public void givenInvalidId_whenGetPatientById_thenThrowNotFoundException() {
//        // Given
//        String invalidId = "invalidId";
//        given(patientRepository.findById(invalidId)).willReturn(Optional.empty());
//
//        // When and Then
//        assertThrows(NotFoundException.class, () -> {
//            patientsService.getPatientById(invalidId);
//        });
//    }
//
//
//    @Test
//    public void deletePatientByIdTest() throws NotFoundException {
//        // Create a patient object with a unique ID
//        Patient patient = new Patient(
//                "CR1",
//                "patient1",
//                "patient1@gmail.com",
//                "06111122111"
//                );
//
//        // Mock the findById method to return the patient object when called with the patient's ID
//        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));
//
//        // Call the deletePatient method with the patient's ID
//        patientsService.deletePatient(patient.getId());
//
//        // Verify that the findById method of the mock object is called once with the patient's ID
//        verify(patientRepository, times(1)).findById(patient.getId());
//
//        // Verify that the delete method of the mock object is called once with the patient object
//        verify(patientRepository, times(1)).delete(patient);
//    }
//
//
//
//    @Test
//    public void addPatientTest() {
//        Patient patient = new Patient(
//                "CR1",
//                "patient1",
//                "patient1@gmail.com",
//                "06111122111"
//                );
//
//        given(patientRepository.save(patient)).willReturn(patient);
//        Patient addedPatient = patientsService.addPatient(patient);
//        verify(patientRepository, times(1)).save(patient);
//        assertThat(addedPatient).isNotNull();
//        assertThat(addedPatient.getId()).isEqualTo(patient.getId());
//    }
//
//    @Test
//    public void updatePatientTest() {
//        Patient patient = new Patient(
//                "CR1",
//                "patient1",
//                "patient1@gmail.com",
//                "06111122111"
//                );
//
//        // update method require get By Id method from repo
//        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));
//        given(patientRepository.save(patient)).willReturn(patient);
//
//        Patient updatedPatient = patientsService.updatePatient(patient.getId(), patient);
//
//        verify(patientRepository, times(1)).save(patient);
//        assertThat(updatedPatient).isNotNull();
//        assertThat(updatedPatient.getId()).isEqualTo(patient.getId());
//    }
//
//
//}
