package com.FinalProject.repository;

import com.FinalProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByDeleteStatusFalseOrderByIDDesc();

    List<Student> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndStudentFINContainingIgnoreCase(
            String name, String surname, String studentFin);

}
