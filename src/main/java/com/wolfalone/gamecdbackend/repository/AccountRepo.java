package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    @Query(value = "select count(*) from tbl_accounts a where email = ? and (status = 1)",
            nativeQuery = true)
    long countByEmailAndActive(String email);


    Account findByEmailAndPassword(String email, String password);
    Optional<Account> findByEmail(String email);

    @Query(value = "select * from tbl_accounts a where a.email = ? and (status = 0)", nativeQuery =
            true)
    Account countByEmailAndPending(String email);

    Account findByEmailAndVerifiedEmailCodeAndStatus(String email, String code, int status);
}
