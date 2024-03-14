package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private List<OrderDto> allOrdersTest = new ArrayList<>();
    private OrderDto oneOrderTest = new OrderDto(111L, "order_number", 222L, new ArrayList<>());

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(allOrdersTest);
    }

    @GetMapping(value = "{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(oneOrderTest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto) {
        System.out.println("The order has been created");
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) {
        oneOrderTest = new OrderDto(111L, "changed_number", 222L, new ArrayList<>());
        return ResponseEntity.ok(oneOrderTest);
    }

    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        System.out.println("Order no." + orderId + " has been deleted");
        return ResponseEntity.ok().build();
    }
}
