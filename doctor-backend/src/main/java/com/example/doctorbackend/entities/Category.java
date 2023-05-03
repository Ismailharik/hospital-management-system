package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private Long numberOfDoctors;
    @ManyToOne
    private List<Doctor> doctors;
}
