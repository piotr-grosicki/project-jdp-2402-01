package com.kodilla.ecommercee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int group_id;
    @ManyToMany
    private List<Order> orders;
}
