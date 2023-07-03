package com.wolfalone.gamecdbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "tbl_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private Date shipDate;
    @CreationTimestamp
    private Date orderDate;
    private int status;

    private String address;
    private String name;
    private String phone;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
}
