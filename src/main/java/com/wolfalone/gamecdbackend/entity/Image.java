package com.wolfalone.gamecdbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tbl_image")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    @SequenceGenerator(name = "image_seq", sequenceName = "image_sequence", allocationSize = 1)
    private int id;
    private String imageUrl;

    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id"
    )
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;
}
