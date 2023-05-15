package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    List<Account> getAllAccount();
}
