package com.wolfalone.gamecdbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Accounts",
        uniqueConstraints = @UniqueConstraint(
                name = "email_id_unique",
                columnNames = "email"
        )
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    private String avatarPath;
    @Column
    private String tokenId;
    @Column
    private int status;
    @Column
    private int role;
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "userId",
            referencedColumnName =  "id"
    )
    private Users user;
}
