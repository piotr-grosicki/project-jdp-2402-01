package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS_GROUPS")
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToMany(targetEntity = Product.class,
            mappedBy = "group",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
