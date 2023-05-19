package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Category;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static int imgId=1;
    /*
    * to enhance security , files should not be stored as they come from the frontend
    * for this reason I will store them by numbers,each time I add image this variable will be
    * incremented will be incremented
    * */

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category createCategory(MultipartFile file,Category category) throws IOException {

        String imagesLocation = System.getProperty("user.home") +"/hospital/categories";
        File f = new File(imagesLocation);
        if(f.exists()){
            System.out.println("stories directory already exist");
        }else{
            System.out.println("create stories directory");
            f.mkdir();
        }
        String imageName = this.imgId++ + ".jpg";
        String imageSrc =imagesLocation +"/"+imageName;
        category.setSrc(imageSrc);
        Files.write(Paths.get(imageSrc), file.getBytes());// add image in server
        return categoryRepository.save(category);

    }
    @Override
    public Category updateCategory(String id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        existingCategory.setDoctors(category.getDoctors());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(String id) {
        Category existingCategory = getCategoryById(id);
        categoryRepository.delete(existingCategory);
    }

}
