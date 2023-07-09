package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AdminOrderTableDTO {
    private int id;
    private String name;
    private String phone;
    private long totalPayment;
    private int status;
    private Long orderDate;
    private Long shipDate;
}
