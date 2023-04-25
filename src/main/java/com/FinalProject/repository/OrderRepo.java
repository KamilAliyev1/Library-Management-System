package com.FinalProject.repository;

import com.FinalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("SELECT o.inProgress FROM order o WHERE o.id=:id")
    boolean isInProgress(@Param("id") Long id);


}
