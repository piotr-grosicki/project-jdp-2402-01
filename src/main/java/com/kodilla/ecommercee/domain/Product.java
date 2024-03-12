package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int group_id;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<Cart> carts;

    @ManyToMany
    private List<Order> orders;

}
