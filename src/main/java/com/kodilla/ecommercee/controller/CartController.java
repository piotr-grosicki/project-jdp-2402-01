package com.kodilla.ecommercee.controller;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.OrderDtoTemporary;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final List<CartDto> carts = new ArrayList<>();

    @GetMapping("/{cartId}")
    public List<String> getCartById(@PathVariable int cartId) {
        List<String> productsName = new ArrayList<>();
        productsName.add("XXX");
        productsName.add("YYY");
       return productsName;

    }
    @PostMapping()
    public void createCart(@RequestBody CartDto cartDto){
        System.out.println("cart created");
    }
    @PutMapping("/add/{cartId}/{productId}")
    public CartDto addProductToCart(@PathVariable int cartId, @PathVariable int productId){
        return new CartDto(1,1,new ArrayList<>());
    }
    @DeleteMapping("/delete/{cartId}/{productId}")
    public void removeProductFromCart(@PathVariable int cartId, @PathVariable int productId) {
        System.out.println("Produkt " + productId + "z koszyka nr " + cartId + " usuniety");
    }

    //po stworzeniu wlasciwej klasie usunac klase OrderDtoTemporary i zastapic wlasciwa
    @PostMapping("/order/{cartId}")
    public OrderDtoTemporary createOrderFromCart(@PathVariable int cartId){
        return new OrderDtoTemporary(1, 1);
    }
}
