package com.wolfalone.gamecdbackend.dto;

public record ChangeUserProfileDTO(
        String name,
        String phone,
        String address
) {
}
