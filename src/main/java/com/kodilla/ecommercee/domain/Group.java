package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Groups")
public class Group {
    @Id
    @Column(name = "Group_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", unique = true)
    @NonNull
    private String name;
    @Column(name = "DESCRIPTION", unique = true)
    @NonNull
    private String description;
   @OneToMany(targetEntity = Product.class,
   mappedBy = "group",
   cascade = CascadeType.ALL,
   fetch = FetchType.LAZY)
    private List<Product> products;

}



