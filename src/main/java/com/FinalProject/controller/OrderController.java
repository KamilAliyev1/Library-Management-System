package com.FinalProject.controller;


import com.FinalProject.dto.OrderGETv1;
import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.model.Order;
import com.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    ResponseEntity<?> add(@RequestBody OrderPOSTv1 dto){
        return new ResponseEntity<>(orderService.add(dto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Long id){
        return ResponseEntity.ok(orderService.get(id));
    }

    @PatchMapping("/{id}")
    void disableProgress(@PathVariable Long id){
        orderService.disableProgress(id);
    }




}
