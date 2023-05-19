package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(String id);
    Category createCategory(MultipartFile file,Category category) throws IOException;
    Category updateCategory(String id, Category category);
    void deleteCategory(String id);
}
