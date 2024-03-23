package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setBlocked(userDto.isBlocked());
        user.setPassword(userDto.getPassword());
        user.setApiKey(userDto.getApiKey());
        user.setActive(userDto.isActive());

        if (userDto.getCartIds() != null) {
            try {
                List<Cart> carts = cartRepository.findAllById(userDto.getCartIds());
                user.setCarts(carts);
            } catch (Exception ex) {

                throw new RuntimeException("Error while retrieving user carts", ex);
            }
        }
        if (userDto.getOrderIds() != null) {
            try {
                List<Order> orders = orderRepository.findAllById(userDto.getOrderIds());
                user.setOrders(orders);
            } catch (Exception ex) {

                throw new RuntimeException("Error while retrieving user orders", ex);
            }
        }

        return user;
    }
}
