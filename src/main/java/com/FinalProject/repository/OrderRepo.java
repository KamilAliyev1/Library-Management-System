package com.FinalProject.repository;

import com.FinalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderRepo extends JpaRepository<Order,Long> {

    @Modifying
    @Query("UPDATE order o SET o.inProgress=false,o.finishedAt=:time where o.id=:id")
        int disableProgress(@Param("id") Long id, @Param("time") LocalDateTime time);


}
