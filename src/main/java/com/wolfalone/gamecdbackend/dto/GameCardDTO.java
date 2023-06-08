package com.wolfalone.gamecdbackend.dto;


import com.wolfalone.gamecdbackend.entity.Game;

import java.util.List;

public record GameCardDTO(
        int id,
        String name,
        int price,
        int quantity,
        String imgUrl,
        List<CategoryDTO> listCategory

) {
}
