package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.domain.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private List<OrderDto> allOrdersTest = new ArrayList<>();
    private OrderDto oneOrderTest = new OrderDto(111, "order_number", 222, new ArrayList<>());

    // GET
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(allOrdersTest);
    }

    @GetMapping(value = "{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(oneOrderTest);
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok().build();
    }

    // PUT
    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) {
        oneOrderTest = new OrderDto(111L, "changed_number", 222, new ArrayList<>());
        return ResponseEntity.ok(oneOrderTest);
    }

    // DELETE
    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        System.out.println("Order " + orderId + " has been deleted");
        return ResponseEntity.ok().build();
    }
}
