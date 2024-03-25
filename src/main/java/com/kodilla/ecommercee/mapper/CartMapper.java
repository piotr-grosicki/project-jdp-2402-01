package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final UserRepository userRepository;

    public Cart mapToCart(final CartDto cartDto) throws CartNotFoundException, UserNotFoundException {
        User user = userRepository.findByIdAndActiveTrue(cartDto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setUser(user);
        return cart;
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(cart.getId(), cart.getUser().getId(), cart.getProducts().stream()
                .map(Product::getId).collect(Collectors.toList()), cart.isActive());
    }
}
