package com.example.cardapio.domain.dto;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.enums.StatusRequest;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(Long id, LocalDateTime dataOrder, StatusRequest status, Double total, UserResponseDTO user, List<FoodResponseDTO> food) {
    public OrderResponseDTO(Order order) {
        this(order.getId(), order.getDataOrder(), order.getStatus(), order.getTotal(), new UserResponseDTO(order.getUser()), order.getFoods().stream().map(FoodResponseDTO::new).toList());
    }
}
