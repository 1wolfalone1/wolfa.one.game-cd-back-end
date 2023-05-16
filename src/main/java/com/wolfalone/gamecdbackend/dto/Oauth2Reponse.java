package com.wolfalone.gamecdbackend.dto;

public record Oauth2Reponse(
        String sub,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        boolean email_verified,
        String locale
){
}
