package com.example.doctorbackend.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "people")
@Data
@AllArgsConstructor
//@NoArgsConstructor
@TypeAlias("patient")
public class Patient extends User {

}

