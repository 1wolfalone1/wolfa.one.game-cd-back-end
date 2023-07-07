package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query(nativeQuery = true, value = "select  o.* from tbl_order o join tbl_users u on u.id = o" +
            ".user_id join tbl_accounts ac ON ac.user_id = u.id where ac.email = ?1 order by o" +
            ".order_date desc")
    List<Order> findAllByEmail(String email);
}
