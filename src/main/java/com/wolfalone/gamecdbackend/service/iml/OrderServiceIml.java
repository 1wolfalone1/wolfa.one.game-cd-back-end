package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.config.constant.StatusOrder;
import com.wolfalone.gamecdbackend.dto.AdminOrderTableDTO;
import com.wolfalone.gamecdbackend.dto.AdminOrderTablePageDTO;
import com.wolfalone.gamecdbackend.dto.OrderDTO;
import com.wolfalone.gamecdbackend.dto.OrderTableDTO;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.entity.Order;
import com.wolfalone.gamecdbackend.entity.OrderDetails;
import com.wolfalone.gamecdbackend.entity.Users;
import com.wolfalone.gamecdbackend.mapper.OrderMapper;
import com.wolfalone.gamecdbackend.repository.GameRepo;
import com.wolfalone.gamecdbackend.repository.OrderDetailsRepo;
import com.wolfalone.gamecdbackend.repository.OrderRepo;
import com.wolfalone.gamecdbackend.repository.UserRepo;
import com.wolfalone.gamecdbackend.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceIml implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;
    @Autowired
    private GameRepo gameRepo;
    @Override
    public ResponseEntity<?> orderGames(OrderDTO orderDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Users user = userRepo.getUsersByEmail(email);
            log.info("order ne {}", orderDTO.toString());
            Order order = Order.builder()
                    .orderDate(new Date())
                    .shipDate(null)
                    .status(StatusOrder.PENDING.getStatus())
                    .phone(orderDTO.deliveryInfo.getPhone())
                    .address(orderDTO.deliveryInfo.getAddress())
                    .name(orderDTO.deliveryInfo.getName())
                    .user(user)
                    .totalPayment(orderDTO.getTotalPayment())
                    .build();
            Order newOrder = orderRepo.save(order);

            log.info("order ne {}", newOrder.getId());
            List<OrderDetails> orderDetailsList = orderDTO.getGames().stream().map(game -> {

                return OrderDetails.builder()
                        .game(
                                Game.builder().id(game.getId()).build()
                        )
                        .order(newOrder)
                        .price(game.getPrice())
                        .quantity(game.getQuantity())
                        .build();
            }).collect(Collectors.toList());
            orderDetailsRepo.saveAll(orderDetailsList);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some thing went wrong");
        }
    }

    @Override
    public ResponseEntity<?> getAllOrder() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            log.info("info {}", email);
            List<Order> listOrder = orderRepo.findAllByEmail(email);
            List<OrderTableDTO> orderTableDTOList =
                    listOrder.stream().map(order -> orderMapper.toOrderTableDTO(order)).collect(Collectors.toList());
            return ResponseEntity.ok(orderTableDTOList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad requestt");
        }
    }

    @Override
    public ResponseEntity<?> getAdminOrderTableDTO(int page) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1
                    , 8
                    , Sort.by(Sort.Direction.DESC
                            , "orderDate"
                            , "shipDate")
            );

            Page<Order> pageAble = orderRepo.findAll(pageRequest);
            List<AdminOrderTableDTO> adminOrderTableDTO = orderMapper.toListAdminOrderTableDTO(
                    pageAble.getContent()
            );

            AdminOrderTablePageDTO adminOrderTablePageDTO = AdminOrderTablePageDTO
                    .builder()
                    .totalElement(pageAble.getTotalElements())
                    .data(adminOrderTableDTO)
                    .build();

            return ResponseEntity.ok(adminOrderTablePageDTO);
        } catch (Exception e) {
            log.info("Erorr {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erorr get admin table " +
                    "orderr data");
        }
    }

    @Override
    public ResponseEntity<?> changeStatusOrder(int id) {
        try {
            Order order = orderRepo.findById(id).get();
            order.setStatus(1);
            orderRepo.save(order);
            return ResponseEntity
                    .ok("ok");
        } catch (Exception e) {
            log.error("Error {}", e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error change status");
        }
    }
}
