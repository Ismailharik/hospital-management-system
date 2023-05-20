package com.example.doctorbackend.entities;


import com.example.doctorbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@TypeAlias("admin")
@Document(collection = "people")

public class Admin extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String id;
//    @NotNull(message = "Patient name required")
//    private String name;
//    @Email(message = "Please enter a valid email")
//    @NotNull(message = "email required")
//    private String email;
//    private String phone;

}

