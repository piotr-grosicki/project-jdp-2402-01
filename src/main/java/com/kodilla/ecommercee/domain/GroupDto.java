package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private List<Long> productsIds;
    private boolean active;
}
