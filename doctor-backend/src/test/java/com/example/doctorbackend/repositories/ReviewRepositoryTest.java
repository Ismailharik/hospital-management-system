package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;


    @Test
    public void findReviewByDoctorTest(){
        Patient patient=new Patient("CR1","ismail","ismail@gmail.com","06111122111");
        Doctor doctor =new Doctor();
        doctor.setId("A");
        doctor.setEmail("notexist@gmail.com");
        doctor.setName("notexist");
        doctor.setSpeciality("diabetes");



     assertThat(reviewRepository.findByDoctorAndPatient(doctor,patient)==null);
    }

}
