package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int group_id;
}
