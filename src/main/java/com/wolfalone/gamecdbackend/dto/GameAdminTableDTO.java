package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class GameAdminTableDTO {
    private int id;
    private String name;
    private int price;
    private int quantity;
}
