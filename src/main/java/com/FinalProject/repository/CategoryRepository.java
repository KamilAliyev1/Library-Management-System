package com.FinalProject.repository;

import com.FinalProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select b from Category b where b.name ilike :name")
    Optional<Category> findByName(@Param("name") String name);


}
