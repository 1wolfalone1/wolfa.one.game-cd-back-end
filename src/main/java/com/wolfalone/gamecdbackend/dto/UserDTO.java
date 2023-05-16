package com.wolfalone.gamecdbackend.dto;


public record UserDTO(
        int id,
        String email,
        String address,
        String phone,
        String name
) {
}
