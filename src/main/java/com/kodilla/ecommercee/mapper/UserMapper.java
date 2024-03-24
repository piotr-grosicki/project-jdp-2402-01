package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kodilla.ecommercee.domain.Cart;
import java.util.List;
import java.util.stream.Collectors;



@Component
@AllArgsConstructor
public class UserMapper {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public UserDto mapToUserDto(User user) {
        List<Long> cartIds = user.getCarts().stream()
                .map(Cart::getId)
                .collect(Collectors.toList());

        List<Long> orderIds = user.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.isBlocked(),
                user.getPassword(),
                user.getApiKey(),
                cartIds,
                orderIds,
                user.isActive()
        );
    }




}
