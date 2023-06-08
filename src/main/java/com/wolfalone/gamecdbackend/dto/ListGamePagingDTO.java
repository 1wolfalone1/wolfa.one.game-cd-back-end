package com.wolfalone.gamecdbackend.dto;

import com.wolfalone.gamecdbackend.entity.Game;

import java.util.List;

public record ListGamePagingDTO(
        int totalPage,
        long totalProduct,
        List<GameCardDTO> games
) {
}
