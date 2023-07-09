package com.wolfalone.gamecdbackend.controller;

import com.wolfalone.gamecdbackend.dto.OrderDTO;
import com.wolfalone.gamecdbackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<?> order(@RequestBody OrderDTO orderDTO) {
        return orderService.orderGames(orderDTO);
    }

    @GetMapping("order")
    public ResponseEntity<?> getOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("admin/order/{page}")
    public ResponseEntity<?> getAdminOrderTableDTO(@PathVariable("page") int page){
        return orderService.getAdminOrderTableDTO(page);
    }

    @PutMapping("admin/order/status/{id}")
    public ResponseEntity<?> changeStatusOrder(@PathVariable("id") int id) {
        return orderService.changeStatusOrder(id);
    }
}
