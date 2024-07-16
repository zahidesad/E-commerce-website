package com.service;

import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println("Attempting to add relationship: Parent ID = " + parentCategoryId + ", Child ID = " + childCategoryId);

        Optional<Category> parentCategoryOpt = categoryRepository.findById(parentCategoryId);
        Optional<Category> childCategoryOpt = categoryRepository.findById(childCategoryId);

        if (parentCategoryOpt.isPresent() && childCategoryOpt.isPresent()) {
            Category parentCategory = parentCategoryOpt.get();
            Category childCategory = childCategoryOpt.get();

            System.out.println("Loaded Parent and Child Categories");
            System.out.println("Parent Category ID: " + parentCategoryId + " Child Categories: " + parentCategory.getChildCategories().size());
            System.out.println("Child Category ID: " + childCategoryId + " Parent Categories: " + childCategory.getParentCategories().size());

            int existingRelationships = categoryRepository.checkCategoryRelationshipExists(parentCategoryId, childCategoryId);

            if (existingRelationships == 0) {
                categoryRepository.saveCategoryRelationship(parentCategoryId, childCategoryId);
                System.out.println("Relationship added successfully.");
            } else {
                System.out.println("Relationship already exists: Parent ID = " + parentCategoryId + ", Child ID = " + childCategoryId);
            }

            // Debugging: İlişkileri kontrol et
            parentCategory = categoryRepository.findById(parentCategoryId).get();
            childCategory = categoryRepository.findById(childCategoryId).get();

            System.out.println("After saving: ");
            System.out.println("Parent Category ID: " + parentCategoryId + " has child categories: ");
            for (Category child : parentCategory.getChildCategories()) {
                System.out.println("Child Category ID: " + child.getId());
            }
            System.out.println("Child Category ID: " + childCategoryId + " has parent categories: ");
            for (Category parent : childCategory.getParentCategories()) {
                System.out.println("Parent Category ID: " + parent.getId());
            }
        } else {
            System.out.println("Parent or Child Category not found.");
        }
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
