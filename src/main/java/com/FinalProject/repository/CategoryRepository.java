package com.FinalProject.repository;

import com.FinalProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByIdDesc();
    List<Category> findCategoriesByNameContainingIgnoreCase(String name);
}
