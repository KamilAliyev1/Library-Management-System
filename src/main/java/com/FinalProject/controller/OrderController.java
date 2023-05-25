package com.FinalProject.controller;


import com.FinalProject.dto.OrderPOSTv1;
import com.FinalProject.mapper.OrderMapper;
import com.FinalProject.model.Order;
import com.FinalProject.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;


    @GetMapping("/add")
    String addPage(Model model) {
        var s = orderMapper.toGetDto(new Order());
        model.addAttribute("order", s);
        return "order-create";
    }

    @PostMapping("/add")
    String addOrder(Model model, @ModelAttribute("order") @Valid OrderPOSTv1 orderPOSTv1) {
        orderService.add(orderPOSTv1);
        return "redirect:/orders";
    }


    @GetMapping("/search/")
    String get(@RequestParam Long id, Model model) {
        model.addAttribute("orders", List.of(orderService.get(id)));
        return "order-list";
    }

    @GetMapping("/{id}/disableProgress")
    String disableProgress(@PathVariable Long id) {
        orderService.disableProgress(id);
        return "redirect:/orders";
    }


    @GetMapping
    String getAll(Model model) {
        var all = orderService.getAll();
        model.addAttribute("orders", all);
        return "order-list";
    }


    @GetMapping("/{id}/delete")
    String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }


    @PostMapping("/{id}/update")
    String update(@PathVariable Long id, @ModelAttribute("order") @Valid OrderPOSTv1 dto, Model model) {

        var order = orderService.update(id, dto);

        model.addAttribute("order", order);

        return "order-update";
    }

    @GetMapping("/{id}/update")
    String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.get(id));
        return "order-update";
    }


}
