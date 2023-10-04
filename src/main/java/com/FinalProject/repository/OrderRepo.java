package com.FinalProject.repository;

import com.FinalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {


    @Query("SELECT o.inProgress FROM order o WHERE o.ID=:id")
    boolean isInProgress(@Param("id") Long id);

    List<Order> findByStudentIDOrBooks_Id(Long studentId, Long bookId);

    List<Order> findAllByOrderByIDDesc();
}
