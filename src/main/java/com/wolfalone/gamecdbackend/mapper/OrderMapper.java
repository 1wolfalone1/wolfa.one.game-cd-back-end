package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.AdminOrderTableDTO;
import com.wolfalone.gamecdbackend.dto.Oauth2Reponse;
import com.wolfalone.gamecdbackend.dto.OrderTableDTO;
import com.wolfalone.gamecdbackend.dto.ViewOrderDetailsDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface OrderMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "totalPayment", source = "totalPayment")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "shipDate", source = "shipDate.time")
    @Mapping(target = "orderDate", source = "orderDate.time")
    OrderTableDTO toOrderTableDTO(Order order);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "totalPayment", source = "totalPayment")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "orderDate", source = "orderDate.time")
    @Mapping(target = "shipDate", source = "shipDate.time")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "name", source = "name")
    ViewOrderDetailsDTO toViewOrderDetailsDTO(Order order);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "totalPayment", source = "totalPayment")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "orderDate", source = "orderDate.time")
    @Mapping(target = "shipDate", source = "shipDate.time")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "name", source = "name")
    AdminOrderTableDTO toAdminTableDTO(Order order);

    List<AdminOrderTableDTO> toListAdminOrderTableDTO(List<Order> content);
}
