package com.wolfalone.gamecdbackend.controller;

import com.wolfalone.gamecdbackend.dto.UserInfoDto;
import com.wolfalone.gamecdbackend.service.UserService;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> changeProfile(@RequestPart("data") UserInfoDto userInfoDto,
                                           @RequestParam(value = "image", required = false)
                                           MultipartFile image) throws IOException {

        return userService.changUserProfile(userInfoDto, image);
    }
}
