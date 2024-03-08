package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDto {
    private int id;
    private String name;
    private String description;
}
