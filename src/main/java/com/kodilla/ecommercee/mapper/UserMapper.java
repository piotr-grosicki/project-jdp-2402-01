package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;
import com.kodilla.ecommercee.domain.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Builder
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

    public List<UserDto> mapToUserDtoList(List<User> users) {
        List<UserDto> userDtoList = users.stream()
                .map(this::mapToUserDto)
                .toList();
        return userDtoList;
    }

    public User mapToUser(UserDto userDto) {
        List<Cart> carts = cartRepository.findAllById(userDto.getCartIds());
        List<Order> orders = orderRepository.findAllById(userDto.getOrderIds());

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setBlocked(userDto.isBlocked());
        user.setPassword(userDto.getPassword());
        user.setApiKey(userDto.getApiKey());
        user.setActive(userDto.isActive());
        user.setCarts(carts);
        user.setOrders(orders);

        return user;
    }
}
