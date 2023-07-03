package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    ResponseEntity<?> orderGames(OrderDTO orderDTO);
}
