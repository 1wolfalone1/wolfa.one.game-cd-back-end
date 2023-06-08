package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceIml implements OrderService {
}
