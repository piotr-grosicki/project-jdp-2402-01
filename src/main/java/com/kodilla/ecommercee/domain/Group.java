package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "GROUPS_TABLE")
public class Group {

    @Id
    @Column(name = "ID", unique = true)
    private int id;
}



