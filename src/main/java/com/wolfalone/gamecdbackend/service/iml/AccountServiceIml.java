package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Override
    public List<Account> getAllAccount() {
        return accountRepo.findAll();
    }
}
