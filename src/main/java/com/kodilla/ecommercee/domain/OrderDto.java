package com.kodilla.ecommercee.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private Long id;
    private String orderNumber;
    private Long userId;
    private List<Integer> orderProductsIds;
}
