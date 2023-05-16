package com.wolfalone.gamecdbackend.dto;

import lombok.Data;


public record Oauth2Token(
        String access_token,
        String token_type
) {
}
