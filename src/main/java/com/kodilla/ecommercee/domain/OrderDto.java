package com.kodilla.ecommercee.domain;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private long id;
    private String orderNumber;
    private long userId;
    private List<Long> orderProductsIds;
}
