package com.example.cardapio.domain.dto;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.enums.StatusRequest;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDTO(LocalDateTime dataOrder, StatusRequest status, Double total, UserIfood user, List<Food> food) {
    public OrderRequestDTO(Order order) {
        this(order.getDataOrder(), order.getStatus(), order.getTotal(), order.getUser(), order.getFoods());
    }
}
