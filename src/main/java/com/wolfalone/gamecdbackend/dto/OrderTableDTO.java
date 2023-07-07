package com.wolfalone.gamecdbackend.dto;

public record OrderTableDTO(
        int id,
        Long totalPayment,
        Long orderDate,
        Long shipDate,
        int status
) {
}
