package com.wolfalone.gamecdbackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DeliveryInfoDTO {
    public String name;
    public String address;
    public String phone;

}
