package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.CartNotFoundException;
import com.kodilla.ecommercee.exception.ProductNotFoundException;
import com.kodilla.ecommercee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public void addCart(final Cart cart) {
        cartRepository.save(cart);
    }

    public Cart getCart(final Long cartId) throws CartNotFoundException {
        return cartRepository.findByIdAndActiveTrue(cartId).orElseThrow(CartNotFoundException::new);
    }

    public List<Product> getProductsFromCart(final Long cartId) throws CartNotFoundException {
        List<Product> productsList = cartRepository.findByIdAndActiveTrue(cartId).map(Cart::getProducts)
                .orElseThrow(CartNotFoundException::new);
        return productsList;
    }

    public Cart addProductToCart(final Long cartId, final Long productId)
            throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productService.getProduct(productId);
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Product getProductFromCart(Long cartId, Long productId)
            throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId).orElseThrow(CartNotFoundException::new);
        for (Product product : cart.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        throw new ProductNotFoundException();
    }

    public Cart deleteProductFromCart(final Long cartId, final Product product) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAndActiveTrue(cartId).orElseThrow(CartNotFoundException::new);
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }
}
