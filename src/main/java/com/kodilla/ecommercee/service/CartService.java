package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public Cart addCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCart(final Long cartId) throws CartNotFoundException {
        return cartRepository.findByIdAndActiveTrue(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
    }

    public List<Product> getProductsFromCart(final Long cartId) throws CartNotFoundException {
        List<Product> productsList = cartRepository.findByIdAndActiveTrue(cartId).map(Cart::getProducts)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        return productsList;
    }

    public Cart addProductToCart(final Long cartId, final Product product) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Product getProductFromCart(Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        for (Product product : cart.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        throw new ProductNotFoundException();
    }

    public Cart deleteProductFromCart(final Long cartId, final Product product) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }
    @Transactional
    public Order createOrderFromCart(final Long cartId) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setProducts(cart.getProducts());
        order.setActive(true);
        cart.setActive(false);
        cartRepository.save(cart);
        return orderRepository.save(order);
    }
}
