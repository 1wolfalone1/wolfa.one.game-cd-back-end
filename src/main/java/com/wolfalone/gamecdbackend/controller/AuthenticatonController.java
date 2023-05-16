package com.wolfalone.gamecdbackend.controller;


import com.wolfalone.gamecdbackend.dto.Oauth2Reponse;
import com.wolfalone.gamecdbackend.dto.Oauth2Token;
import com.wolfalone.gamecdbackend.dto.UserDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Users;
import com.wolfalone.gamecdbackend.mapper.UserMapper;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.repository.UserRepo;
import com.wolfalone.gamecdbackend.service.AccountService;
import com.wolfalone.gamecdbackend.service.iml.JwtService;
import com.wolfalone.gamecdbackend.util.MyLogger;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticatonController {

    @Autowired
    private MyLogger log;
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;


    @GetMapping("/login")
    public ResponseEntity<List<UserDTO>> login() {
        List<Account> list = accountService.getAllAccount();
        List<UserDTO> listDto = list.stream().map(a -> userMapper.toDTO(a.getUser(), a)).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);

    }

    @GetMapping("/send")
    public ResponseEntity<Object> send(HttpServletRequest request) {

        return ResponseEntity.ok("123123");
    }

    @PostMapping("/oauth2/google")
    public ResponseEntity<Object> a(@RequestBody Oauth2Token a,HttpServletRequest request, HttpServletResponse response) {
        RestTemplate _request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(a.access_token());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Oauth2Reponse> k = _request.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, Oauth2Reponse.class);
        System.out.println(a);
        System.out.println(k.getBody());
        Oauth2Reponse _response = k.getBody();
        if(request.getCookies() != null) {
            Arrays.stream(request.getCookies()).forEach(c -> {
                System.out.println(c + "1111111111111111111111111");
            });
        } else  {
            String tmp = request.getHeader("Authorization");
            if(request.getHeader("Authorization") == "[object Object]") {
                log.log().info(tmp +"11111111111111111111111111111");
            } else {
                log.log().info(tmp +"22222222222222222222222222222222222222");

            }
        }
        Account acc = accountService.getAccountByEmail(_response.email());
        String token = "false";
        if (acc != null) {
            token = jwtService.generateToken(acc);
        }
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);
        System.out.println(token);
        return ResponseEntity.ok(token);
    }
}
