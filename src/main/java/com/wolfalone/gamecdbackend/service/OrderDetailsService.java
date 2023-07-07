package com.wolfalone.gamecdbackend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailsService {
    ResponseEntity<?> getOrderDetailsByOrderId(int orderId);
}
