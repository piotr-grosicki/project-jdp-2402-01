package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private Long id;
    private Long userId;
    private List<Long> productsIds;
    private boolean active;
}
