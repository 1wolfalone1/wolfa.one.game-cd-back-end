package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    Account getAccountByEmail(String email);
    List<Account> getAllAccount();
}
