package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull(message = "please type category name")
    private String name;
//    private Long numberOfDoctors; // the size of doctors array
    private String src;
    @DBRef
    @Field("doctor_id")
    private List<Doctor> doctors=new ArrayList<>();
}
