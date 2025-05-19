package com.service;

import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CategoryService.
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock CategoryRepository categoryRepo;
    @Mock ProductRepository productRepo;

    @InjectMocks CategoryService categoryService;

    private Category cat(long id, String name) {
        Category c = new Category(); c.setId(id); c.setName(name);
        return c;
    }
    private Product prod(long id) {
        Product p = new Product(); p.setId(id); return p;
    }

    /* -------------------------------------------------
     * 1) getParentCategories / getChildCategories
     * --------------------------------------------- */
    @Test
    void parent_vs_child_lists() {
        Category parent = cat(1,"Parent");
        Category child  = cat(2,"Child"); child.getParentCategories().add(parent);
        parent.getChildCategories().add(child);

        when(categoryRepo.findAll()).thenReturn(List.of(parent, child));

        assertThat(categoryService.getParentCategories()).containsExactly(parent);
        assertThat(categoryService.getChildCategories()).containsExactly(child);
    }

    /* -------------------------------------------------
     * 2) getProductsByCategory
     * --------------------------------------------- */
    @Test
    void getProductsByCategory_includes_children() {
        Category parent = cat(1,"Electronics");
        Category child  = cat(2,"Phones");
        parent.getChildCategories().add(child);
        child.getParentCategories().add(parent);

        Product p1 = prod(10); Product p2 = prod(20);
        parent.getProducts().add(p1);
        child.getProducts().add(p2);

        when(categoryRepo.findById(1L)).thenReturn(Optional.of(parent));

        List<Product> result = categoryService.getProductsByCategory(1L);

        assertThat(result).containsExactlyInAnyOrder(p1, p2);
    }

    /* -------------------------------------------------
     * 3) getCategoryProductCount & recursive total
     * --------------------------------------------- */
    @Test
    void product_counts_across_hierarchy() {
        Category root = cat(1,"Root");
        Category child = cat(2,"Child");
        Category grand = cat(3,"Grand");
        root.getChildCategories().add(child);
        child.getParentCategories().add(root);
        child.getChildCategories().add(grand);
        grand.getParentCategories().add(child);

        root.getProducts().add(prod(1));
        child.getProducts().add(prod(2));
        grand.getProducts().add(prod(3));

        /* non-recursive count (root + level-1 children) */
        int lvl1 = categoryService.getCategoryProductCount(root);
        assertThat(lvl1).isEqualTo(2);   // root + child

        /* recursive count (all descendants) */
        int total = categoryService.getTotalProductCount(root);
        assertThat(total).isEqualTo(3);  // root + child + grand
    }

    /* -------------------------------------------------
     * 4) addCategory
     * --------------------------------------------- */
    @Test
    void addCategory_saves_and_returns() {
        when(categoryRepo.save(any(Category.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        categoryService.addCategory("Gadgets");

        verify(categoryRepo).save(argThat(cat -> "Gadgets".equals(cat.getName())));
    }

    /* -------------------------------------------------
     * 5) setCategoryRelationship
     * --------------------------------------------- */
    @Nested
    class Relationship {

        Category parent = cat(1, "Parent");
        Category child  = cat(2, "Child");

        @Test
        void saves_relationship_when_absent() {
            // ① both categories must be found
            when(categoryRepo.findById(1L)).thenReturn(Optional.of(parent));
            when(categoryRepo.findById(2L)).thenReturn(Optional.of(child));

            // ② relationship does not yet exist
            when(categoryRepo.checkCategoryRelationshipExists(1L, 2L))
                    .thenReturn(0);

            categoryService.setCategoryRelationship(1L, 2L);

            verify(categoryRepo).saveCategoryRelationship(1L, 2L);
        }

        @Test
        void ignores_when_relationship_exists() {
            when(categoryRepo.findById(1L)).thenReturn(Optional.of(parent));
            when(categoryRepo.findById(2L)).thenReturn(Optional.of(child));

            // existing link → service should skip save
            when(categoryRepo.checkCategoryRelationshipExists(1L, 2L))
                    .thenReturn(1);

            categoryService.setCategoryRelationship(1L, 2L);

            verify(categoryRepo, never())
                    .saveCategoryRelationship(anyLong(), anyLong());
        }
    }

}
