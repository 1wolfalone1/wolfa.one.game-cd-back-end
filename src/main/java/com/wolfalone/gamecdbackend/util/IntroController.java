package com.wolfalone.gamecdbackend.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IntroController {

    @GetMapping
    public String getIntro() {
        return "Welcome to GameCD backend 3:50 - the front-end domain now move to https://www" +
                ".game-cd" +
                ".store";
    }
}
