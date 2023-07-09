package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminUpdateDTO {
    private GameForAdminUpdateDTO data;
    private List<ImageUpdateDTO> images;
}
