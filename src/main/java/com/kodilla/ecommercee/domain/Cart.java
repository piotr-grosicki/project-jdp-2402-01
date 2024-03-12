package com.kodilla.ecommercee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CARTS")
public class Cart {
    @Id
    @Column(name = "CART_ID", unique = true)
    private int id;

    @ManyToMany
    private List<Product> products;

}
