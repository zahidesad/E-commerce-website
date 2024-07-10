package com.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_relationship",
            joinColumns = @JoinColumn(name = "parent_category_id"),
            inverseJoinColumns = @JoinColumn(name = "child_category_id")
    )
    private Set<Category> childCategories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_relationship",
            joinColumns = @JoinColumn(name = "child_category_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_category_id")
    )
    private Set<Category> parentCategories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public Set<Category> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(Set<Category> parentCategories) {
        this.parentCategories = parentCategories;
    }
}
