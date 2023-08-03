package com.FinalProject.repository;

import com.FinalProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByStudentFIN(String fin);

    List<Student> getStudentsByDeleteStatusFalse();
}
