package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private  int id;
    private String username;
    private String password;
    private boolean blocked;
    private String apiKey;
}
