package com.wolfalone.gamecdbackend.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameDataUpdateDTO {
    private List<Integer> category;
    private String description;
    private int id;
    private String name;
    private int price;
    private int quantity;
    private List<Integer> oldImages;
}
