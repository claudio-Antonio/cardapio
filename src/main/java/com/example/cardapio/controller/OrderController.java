package com.example.cardapio.controller;

import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.dto.OrderRequestDTO;
import com.example.cardapio.domain.dto.OrderResponseDTO;
import com.example.cardapio.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<OrderResponseDTO> response = orderService.findAll().stream().map(OrderResponseDTO::new).toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        OrderResponseDTO response = new OrderResponseDTO(orderService.findById(id));
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping
    public ResponseEntity<OrderResponseDTO> saveNewOrder(@RequestBody OrderRequestDTO request) {
        Order o = new Order(request);
        OrderResponseDTO response = new OrderResponseDTO(orderService.save(o));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@RequestBody OrderRequestDTO request) {
        Order order = new Order(request);
        orderService.delete(order);
        return ResponseEntity.noContent().build();
    }

}
