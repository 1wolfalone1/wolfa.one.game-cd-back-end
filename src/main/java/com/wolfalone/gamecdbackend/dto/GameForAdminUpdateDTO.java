package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameForAdminUpdateDTO {
    private int id;
    private String name;
    private List<CategoryDTO> category;
    private String description;
    private int price;
    private int quantity;
}
