package com.repository;

import com.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category_relationship (parent_category_id, child_category_id) VALUES (:parentCategoryId, :childCategoryId)", nativeQuery = true)
    void saveCategoryRelationship(@Param("parentCategoryId") Long parentCategoryId, @Param("childCategoryId") Long childCategoryId);

    @Query(value = "SELECT COUNT(*) FROM category_relationship WHERE parent_category_id = :parentCategoryId AND child_category_id = :childCategoryId", nativeQuery = true)
    int checkCategoryRelationshipExists(@Param("parentCategoryId") Long parentCategoryId, @Param("childCategoryId") Long childCategoryId);
}
