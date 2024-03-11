package com.kodilla.ecommercee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id private  int id;
    private String username;
    private String password;
    private boolean blocked;
    private String apiKey;
    @OneToMany
    private List<Order> orders;
}
