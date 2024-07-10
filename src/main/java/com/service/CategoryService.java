package com.service;

import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            Hibernate.initialize(category.getProducts());
            Hibernate.initialize(category.getChildCategories());
            Hibernate.initialize(category.getParentCategories());
        }
        return categories;
    }


    public void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void setCategoryRelationship(Long parentCategoryId, Long childCategoryId) {
        Category parentCategory = categoryRepository.findById(parentCategoryId).orElseThrow();
        Category childCategory = categoryRepository.findById(childCategoryId).orElseThrow();
        parentCategory.getChildCategories().add(childCategory);
        childCategory.getParentCategories().add(parentCategory);
        categoryRepository.save(parentCategory);
        categoryRepository.save(childCategory);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional()
    public List<Category> getParentCategories() {
        return getAllCategories().stream()
                .filter(category -> category.getParentCategories().isEmpty())
                .collect(Collectors.toList());
    }
    @Transactional()
    public List<Category> getChildCategories() {
        return getAllCategories().stream()
                .filter(category -> !category.getParentCategories().isEmpty())
                .collect(Collectors.toList());
    }

    public int getTotalProductCount(Category category) {
        int totalProductCount = category.getProducts().size();
        for (Category childCategory : category.getChildCategories()) {
            totalProductCount += getTotalProductCount(childCategory);
        }
        return totalProductCount;
    }

    public int getCategoryProductCount(Category category) {
        Set<Category> allCategories = new HashSet<>();
        allCategories.add(category);
        allCategories.addAll(category.getChildCategories());

        int productCount = 0;
        for (Category cat : allCategories) {
            productCount += cat.getProducts().size();
        }
        return productCount;
    }

    @Transactional()
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            Hibernate.initialize(category.getProducts());
            Set<Product> products = new HashSet<>(category.getProducts());
            for (Category childCategory : category.getChildCategories()) {
                Hibernate.initialize(childCategory.getProducts());
                products.addAll(childCategory.getProducts());
            }
            return new ArrayList<>(products);
        }
        return Collections.emptyList();
    }
}
