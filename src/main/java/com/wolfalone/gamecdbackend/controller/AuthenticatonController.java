package com.wolfalone.gamecdbackend.controller;


import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticatonController {

    @Autowired
    private AccountService accountService;
    @GetMapping("/login")
    public ResponseEntity<List<Account>> login() {
        List<Account> list = accountService.getAllAccount();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/send")
    public ResponseEntity<Object> send(HttpServletRequest request) {
        return ResponseEntity.ok(request.getAttribute("a"));
    }
}
