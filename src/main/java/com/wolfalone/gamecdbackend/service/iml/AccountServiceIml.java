package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.service.AccountService;
import com.wolfalone.gamecdbackend.util.MyLogger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceIml implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private MyLogger myLogger;

    @Override
    public Account getAccountByEmail(String email) {
        Optional<Account> acc = accountRepo.findByEmail(email);
        return acc.orElse(null);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepo.findAll();
    }
}
