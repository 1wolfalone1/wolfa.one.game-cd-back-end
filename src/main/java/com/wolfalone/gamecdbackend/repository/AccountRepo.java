package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);
}
