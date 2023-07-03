package com.wolfalone.gamecdbackend;

import com.wolfalone.gamecdbackend.config.constant.Constant;
import com.wolfalone.gamecdbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@EnableConfigurationProperties(Constant.class)
public class GameCdBackEndApplication {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(GameCdBackEndApplication.class, args);
    }

}
