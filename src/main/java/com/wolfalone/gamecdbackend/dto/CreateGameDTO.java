package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class CreateGameDTO {
    private String name;
    private List<Integer> category;
    private String description;
    private int price;
    private int quantity;
}
