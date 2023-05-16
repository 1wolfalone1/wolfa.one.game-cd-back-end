package com.wolfalone.gamecdbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "account")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Account account;
}
