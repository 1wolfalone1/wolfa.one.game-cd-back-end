package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.dto.GameCardDTO;
import com.wolfalone.gamecdbackend.dto.ViewOrderDetailsDTO;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.entity.Order;
import com.wolfalone.gamecdbackend.mapper.GameMapper;
import com.wolfalone.gamecdbackend.mapper.OrderMapper;
import com.wolfalone.gamecdbackend.repository.OrderRepo;
import com.wolfalone.gamecdbackend.service.CategoryService;
import com.wolfalone.gamecdbackend.service.OrderDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailsServiceIml implements OrderDetailsService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseEntity<?> getOrderDetailsByOrderId(int orderId) {
        try {
            Order order = orderRepo.findById(orderId).get();
            ViewOrderDetailsDTO newOrderDetailsDTO = orderMapper.toViewOrderDetailsDTO(order);
            List<ViewOrderDetailsDTO.GameDetailsDTO> gameDetailsDTOList = order.getOrderDetailsList().stream().map(orderDetails -> {
                Game game = orderDetails.getGame();
                GameCardDTO gameCardDTO = gameMapper.toDTO(game, game.getImages().get(game.getImages().size() - 1).getImageUrl(), categoryService.mapListCateToDTO(game.getCategories()));
                return ViewOrderDetailsDTO.GameDetailsDTO.builder()
                        .price(orderDetails.getPrice())
                        .quantity(orderDetails.getQuantity())
                        .game(gameCardDTO)
                        .build();
            }).collect(Collectors.toList());
            newOrderDetailsDTO.setGames(gameDetailsDTOList);
            return ResponseEntity.ok(newOrderDetailsDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }
}
