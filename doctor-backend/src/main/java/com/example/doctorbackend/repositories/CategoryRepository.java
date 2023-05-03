
package com.example.doctorbackend.repositories;


import com.example.doctorbackend.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<Category,String> {
}
