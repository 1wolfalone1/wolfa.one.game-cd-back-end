package com.wolfalone.gamecdbackend.controller;


import com.wolfalone.gamecdbackend.config.constant.ApiConstant;
import com.wolfalone.gamecdbackend.dto.Oauth2Reponse;
import com.wolfalone.gamecdbackend.dto.Oauth2Token;
import com.wolfalone.gamecdbackend.dto.UserDTO;
import com.wolfalone.gamecdbackend.dto.UserRegistrationDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Users;
import com.wolfalone.gamecdbackend.mapper.UserMapper;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.repository.UserRepo;
import com.wolfalone.gamecdbackend.service.AccountService;
import com.wolfalone.gamecdbackend.service.EmailService;
import com.wolfalone.gamecdbackend.service.iml.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(origins = ApiConstant.FRONT_END_URL, allowCredentials = "true")
public class AuthenticatonController {


    @Autowired
    private EmailService emailService;

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

    @PostMapping("/auth/login")
    public ResponseEntity<UserDTO> loginByEmailPassword(@RequestBody UserDTO userAuthentication) {
        try {
            UserDTO responseUser = accountService.authenticate(userAuthentication);
            if (responseUser != null) {

                return ResponseEntity.ok(responseUser);
            } else {

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            }
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("errorServer", "Some thing wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(null);
        }

    }

    @PostMapping("/oauth2/google")
    public ResponseEntity<UserDTO> a(@RequestBody Oauth2Token payload, HttpServletRequest request,
                                     HttpServletResponse response) {
        RestTemplate _request = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(payload.access_token());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Oauth2Reponse> oauth2UserResource = _request.exchange("https://www" +
                ".googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, Oauth2Reponse.class);
        Oauth2Reponse _response = oauth2UserResource.getBody();

        Account acc = accountService.getAccountByEmail(_response.email());
        UserDTO user = null;
        String token = "";
        if (acc != null) {
            token = jwtService.generateToken(acc);
            System.out.println(token + "      111111111111111111");
            acc.setTokenId(token);
            user = userMapper.toDTO(acc.getUser(), acc);
        } else {
           Account newAccount =  accountService.registerNewAccountByOauth2(_response);
            token = jwtService.generateToken(newAccount);
            newAccount.setTokenId(token);
            user = userMapper.toDTO(newAccount.getUser(), newAccount);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registrationAccount(@RequestBody UserRegistrationDTO payload) {
        Optional<UserDTO> user = accountService.registerNewAccount(payload);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Duplicated Email!");
        }
        System.out.println(user + "user ne");
        return ResponseEntity.ok(user.get().email());
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<UserDTO> verifyEmail(@Param("email") String email, @Param("code") String code) {
        Optional<UserDTO> user = accountService.verifyEmail(email, code);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null));
    }
}
