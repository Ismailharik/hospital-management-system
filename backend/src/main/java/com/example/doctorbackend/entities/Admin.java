package com.example.doctorbackend.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@TypeAlias("admin")
@Document(collection = "people")

public class Admin extends User {

}

