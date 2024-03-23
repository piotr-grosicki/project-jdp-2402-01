package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private boolean blocked;
    private String password;
    private String apiKey;
    private  List<Long> cartIds;
    private List<Long> orderIds;
    private boolean active;

}
