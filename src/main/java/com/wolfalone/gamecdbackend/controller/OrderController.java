package com.wolfalone.gamecdbackend.controller;

import com.wolfalone.gamecdbackend.dto.OrderDTO;
import com.wolfalone.gamecdbackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderDTO orderDTO) {
        return orderService.orderGames(orderDTO);
    }

    @GetMapping
    public ResponseEntity<?> getOrder() {
        return orderService.getAllOrder();
    }
}
