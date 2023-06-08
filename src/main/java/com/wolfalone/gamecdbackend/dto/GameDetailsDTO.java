package com.wolfalone.gamecdbackend.dto;

import java.util.List;

public record GameDetailsDTO(
        int id,
        String description,
        String name,
        int price,
        int quantity,
        List<ImageDTO> imageDTOList,
        List<CategoryDTO> categoryDTOList
) {
}
