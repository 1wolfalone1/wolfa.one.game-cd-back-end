package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    @Query(nativeQuery = true, value=
            "select u.* from tbl_users u join tbl_accounts ON tbl_accounts.user_id = u" +
                    ".id where tbl_accounts.email = ?1")
    Users getUsersByEmail(String email);
}
