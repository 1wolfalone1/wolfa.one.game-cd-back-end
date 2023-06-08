package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.service.OrderDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailsServiceIml implements OrderDetailsService {
}
