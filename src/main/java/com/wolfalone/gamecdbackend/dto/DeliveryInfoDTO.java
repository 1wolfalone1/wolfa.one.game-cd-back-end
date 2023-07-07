package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DeliveryInfoDTO {
    private String name;
    private String address;
    private String phone;

}
