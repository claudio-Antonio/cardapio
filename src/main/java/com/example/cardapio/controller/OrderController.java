package com.example.cardapio.controller;

import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.dto.OrderRequestDTO;
import com.example.cardapio.domain.dto.OrderResponseDTO;
import com.example.cardapio.domain.enums.StatusRequest;
import com.example.cardapio.repositories.UserIfoodRepository;
import com.example.cardapio.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    private final OrderService orderService;
    private final UserIfoodRepository userRepository;

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
        // Pega o usuário autenticado do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Busca o usuário completo do banco (com role)
        UserIfood user = (UserIfood) userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Cria o pedido com o usuário completo do banco
        Order order = new Order();
        order.setDataOrder(LocalDateTime.now());
        order.setStatus(StatusRequest.PENDENTE);
        order.setTotal(request.total());
        order.setUser(user); // Usuário completo do banco
        order.setFoods(request.food());

        OrderResponseDTO response = new OrderResponseDTO(orderService.save(order));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = new Order();
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
