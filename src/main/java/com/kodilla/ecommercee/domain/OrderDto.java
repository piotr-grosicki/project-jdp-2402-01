package com.kodilla.ecommercee.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private int id;
    private String orderNumber;
    private User user;
    private List<Product> products;
}
