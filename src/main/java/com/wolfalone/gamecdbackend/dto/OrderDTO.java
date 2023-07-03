package com.wolfalone.gamecdbackend.dto;

import com.wolfalone.gamecdbackend.entity.Game;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@ToString
public class OrderDTO {
    public List<GameDeliveryDTO> games;
    public Long totalPayment;
    public DeliveryInfoDTO deliveryInfo;

    @Data
    @ToString
    private static class GameDeliveryDTO {
        public Long id;
        public Long quantity;
    }
}
