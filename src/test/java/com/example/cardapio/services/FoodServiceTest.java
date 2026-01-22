package com.example.cardapio.services;

import com.example.cardapio.domain.Food;
import com.example.cardapio.repositories.FoodRepository;

import com.example.cardapio.util.Creator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {
    @Mock
    private FoodRepository foodRepositoryMock;

    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    void setUp() {
        BDDMockito.lenient().when(foodRepositoryMock.findAll()).thenReturn(List.of(Creator.foodCreator()));

        BDDMockito.lenient().when(foodRepositoryMock.save(ArgumentMatchers.any(Food.class))).thenReturn(Creator.foodCreator());

        BDDMockito.lenient().when(foodRepositoryMock.findByTitle(ArgumentMatchers.anyString())).thenReturn(Creator.foodCreator());
    }

    @Test
    @DisplayName("Should return list of orders when successfully")
    void findAll() {
        Food food = Creator.foodCreator();

        List<Food> foods = foodService.findAll();

        Assertions.assertThat(foods).isNotEmpty().hasSize(1).contains(food);
    }

    @Test
    @DisplayName("Should save order object successfully")
    void save() {
        Food food = Creator.foodCreator();

        foodService.save(food);

        Food resultSearch = foodService.findByTitle(food.getTitle());

        Assertions.assertThat(resultSearch).isNotNull().isEqualTo(food);
    }

    @Test
    @DisplayName("Should find order by title successfuly")
    void findByTitleCase1() {
        Food food = Creator.foodCreator();

        Food resultSearch = foodService.findByTitle(food.getTitle());

        Assertions.assertThat(resultSearch).isNotNull().isEqualTo(food);
    }

    @Test
    @DisplayName("Should not find order by title")
    void findByTitleCase2() {
        Food resultSearch = foodService.findByTitle(null);

        Assertions.assertThat(resultSearch).isNull();
    }

}