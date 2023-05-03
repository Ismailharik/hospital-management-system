package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(String id);
    Category createCategory(Category category);
    Category updateCategory(String id, Category category);
    void deleteCategory(String id);
}
