package com.service;

import com.model.Category;
import com.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void setCategoryRelationship(Long parentCategoryId, Long childCategoryId) {
        Category parentCategory = categoryRepository.findById(parentCategoryId).orElseThrow(() -> new RuntimeException("Parent category not found"));
        Category childCategory = categoryRepository.findById(childCategoryId).orElseThrow(() -> new RuntimeException("Child category not found"));

        parentCategory.getChildCategories().add(childCategory);
        categoryRepository.save(parentCategory);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
}
