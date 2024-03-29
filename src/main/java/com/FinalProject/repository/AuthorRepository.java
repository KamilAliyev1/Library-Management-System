package com.FinalProject.repository;

import com.FinalProject.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Authors, Long> {

    List<Authors> findAllByDeleteFalseOrderByIdDesc();

    @Query("SELECT c from Authors c WHERE lower(c.fullName) LIKE CONCAT('%', :query, '%')")
    List<Authors> searchAuthors(String query);
}
