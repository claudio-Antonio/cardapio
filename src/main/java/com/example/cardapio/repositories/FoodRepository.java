package com.example.cardapio.repositories;

import com.example.cardapio.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    public Food findByTitle(String title);
}
