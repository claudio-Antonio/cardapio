package com.example.cardapio.domain.dto;

import com.example.cardapio.domain.Food;

public record FoodRequestDTO(String title, String image, Integer price) {

    public FoodRequestDTO(Food food) {
        this(food.getTitle(), food.getImage(), food.getPrice());
    }
}
