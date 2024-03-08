package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int group_id;
}
