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
    @Column(name = "ID", unique = true)
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
    @JoinColumn(name = "GROUP_ID")
    private Group group;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "CARTS_HAS_PRODUCTS",
            joinColumns = {@JoinColumn(name = "PRODUCTS_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CARTS_ID", referencedColumnName = "ID")}
    )
    private List<Cart> carts;




    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ORDERS_HAS_PRODUCTS",
            joinColumns = {@JoinColumn(name = "PRODUCTS_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ORDERS_ID", referencedColumnName = "ID")}
    )
    private List<Order> orders;



}
