package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageUpdateDTO {
    private int id;
    private String src;
}
