package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BLOCKED", nullable = false)
    private boolean blocked = false;

    @Column(name = "API_KEY")
    private String apiKey;

    @OneToMany(
            targetEntity = Cart.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    public User(String username, String password, String apiKey) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
    }

    @Transient
    private LocalDateTime apiKeyExpiration;
}
