package com.wolfalone.gamecdbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Accounts",
        uniqueConstraints = @UniqueConstraint(
                name = "email_id_unique",
                columnNames = "email"
        )
)
@ToString(exclude = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account  implements UserDetails {

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

    public String getRole() {
        String _role = "GUEST";
        if(this.role == 1) {
            _role = "ADMIN";
        } else if(this.role == 0) {
            _role = "USER";
        }
        return _role;
    }
    @OneToOne(
            optional = false
    )
    @JoinColumn(
            name = "userId",
            referencedColumnName =  "id"
    )
    @JsonManagedReference
    private Users user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
