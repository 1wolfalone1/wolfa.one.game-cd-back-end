package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class PageAdminTableDTO {
    private List<GameAdminTableDTO> list;
    private long totalProduct;
    private int totalPage;
}
