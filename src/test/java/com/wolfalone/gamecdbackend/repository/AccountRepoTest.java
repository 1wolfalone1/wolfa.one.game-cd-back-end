package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepoTest {
    @Autowired
    private AccountRepo accountRepo;

    @Test
    public void fetchAll() {
        List<Account> list = accountRepo.findAll();
        System.out.println(list.get(0).getUser());
    }
}