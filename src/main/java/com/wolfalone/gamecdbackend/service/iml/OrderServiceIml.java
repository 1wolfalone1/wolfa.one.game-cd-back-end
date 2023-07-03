package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.dto.OrderDTO;
import com.wolfalone.gamecdbackend.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceIml implements OrderService {
    @Override
    public ResponseEntity<?> orderGames(OrderDTO orderDTO) {
        log.info("order ne {}", orderDTO.toString());
        return null;
    }
}
