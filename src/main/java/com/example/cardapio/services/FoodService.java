package com.example.cardapio.services;

import com.example.cardapio.domain.Food;
import com.example.cardapio.repositories.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public void save(Food food) {
        foodRepository.save(food);
    }

    public Food findByTitle(String name) {
        return foodRepository.findByTitle(name);
    }
}
