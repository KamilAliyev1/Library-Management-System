package com.FinalProject.repository;

import com.FinalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;




public interface OrderRepo extends JpaRepository<Order,Long> {

    @Modifying
    @Query("UPDATE order o SET o.inProgress=false,o.finishedAt=:time where o.ID=:id")
    int disableProgress(@Param("id") Long id, @Param("time") LocalDateTime time);


    @Query("SELECT o.inProgress FROM order o WHERE o.ID=:id")
    boolean isInProgress(@Param("id") Long id);


}
