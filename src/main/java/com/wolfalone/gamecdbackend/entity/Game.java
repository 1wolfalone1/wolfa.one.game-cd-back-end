package com.wolfalone.gamecdbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_game")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;
    private String description;
    private int quantity;

    @ManyToMany(mappedBy = "games")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<OrderDetails> orderDetails;
}
