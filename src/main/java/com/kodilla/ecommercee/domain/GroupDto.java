package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> productsIds = new ArrayList<>();
    private boolean active;
}
