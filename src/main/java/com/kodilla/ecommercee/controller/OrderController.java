package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private List<OrderDto> allOrdersTest = new ArrayList<>();
    private OrderDto oneOrderTest = new OrderDto();

    // GET
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return allOrdersTest;
    }

    @GetMapping(value = "{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return oneOrderTest;
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return new OrderDto();
    }

    // PUT
    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return oneOrderTest = new OrderDto(111L, "order_number", 666L, new ArrayList<>());
    }

    // DELETE
    @DeleteMapping(value = "{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        System.out.println("Order " + orderId + " has been deleted");
    }
}
