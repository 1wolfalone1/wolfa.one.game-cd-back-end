package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class AdminOrderTablePageDTO {
    private List<AdminOrderTableDTO> data;
    private long totalElement;
 }
