package com.wolfalone.gamecdbackend.dto;

import com.wolfalone.gamecdbackend.entity.Game;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@ToString
public class ViewOrderDetailsDTO {
    private int orderId;
    private Long orderDate;
    private Long shipDate;
    private int status;
    private String address;
    private String name;
    private String phone;
    private Long totalPayment;

    private List<GameDetailsDTO> games;

    @Data
    @ToString
    @Builder
    public static class GameDetailsDTO {
        private GameCardDTO game;
        private int quantity;
        private Long price;
    }
}
