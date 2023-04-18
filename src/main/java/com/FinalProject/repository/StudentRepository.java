package com.FinalProject.repository;

import com.FinalProject.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query(value = "select * from Student ",nativeQuery = true)
    List<Student> getStudents();
}
