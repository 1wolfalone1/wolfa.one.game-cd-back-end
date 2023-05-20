package com.wolfalone.gamecdbackend.dto;

public record UserRegistrationDTO(
        String email,
        String name,
        String address,
        String password,
        String confirmPassword,
        String phone
) {
}
