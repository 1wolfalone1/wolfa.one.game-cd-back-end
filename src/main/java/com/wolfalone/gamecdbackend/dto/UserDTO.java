package com.wolfalone.gamecdbackend.dto;


import lombok.Builder;
import lombok.NoArgsConstructor;

public record UserDTO(
        int id,
        String email,
        String address,
        String phone,
        String name,
        String token,
        String image,
        String role
) {

    @Builder public UserDTO {}

}
