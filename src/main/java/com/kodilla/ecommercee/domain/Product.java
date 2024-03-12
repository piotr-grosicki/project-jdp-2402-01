package com.kodilla.ecommercee.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", unique = true)
    private Long id;

    @NonNull
    @Column(name = "NAME")
    private String name;

    @NonNull
    @Column(name = "DESCRIPTION")
    private String description;

    @NonNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID")
    private Group group;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<Cart> carts;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<Order> orders;

}