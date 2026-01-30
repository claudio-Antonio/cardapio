package com.example.cardapio.controller;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.dto.FoodRequestDTO;
import com.example.cardapio.domain.dto.FoodResponseDTO;
import com.example.cardapio.services.FoodService;
import com.example.cardapio.util.Creator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FoodControllerTest {
    @Mock
    private FoodService foodServiceMock;

    @InjectMocks
    private FoodController foodController;

    private final FoodResponseDTO foodResponseDTO = new FoodResponseDTO(Creator.foodCreator());

    private final FoodRequestDTO foodRequestDTO = new FoodRequestDTO(Creator.foodCreator());

    @BeforeEach
    void setUp() {
        List<Food> foods = List.of(Creator.foodCreator());
        BDDMockito.lenient().when(foodServiceMock.findAll()).thenReturn(foods);

        BDDMockito.lenient().doNothing().when(foodServiceMock).save(ArgumentMatchers.any(Food.class));

        BDDMockito.lenient().when(foodServiceMock.findByTitle(ArgumentMatchers.anyString())).thenReturn(Creator.foodCreator());
    }

    @Test
    @DisplayName("Should return list of food successfully")
    void getAll() {
        ResponseEntity<List<FoodResponseDTO>> list = foodController.getAll();

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("Should Save food Successfully")
    void saveFood() {
        Assertions.assertThatCode(() -> foodController.saveFood(foodRequestDTO)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should find food by name successfully")
    void findByName() {
        Food food = Creator.foodCreator();

        ResponseEntity<FoodResponseDTO> resultSearch = foodController.findByName(food.getTitle());

        Assertions.assertThat(resultSearch).isNotNull();
        Assertions.assertThat(resultSearch.getBody().title()).isEqualTo(food.getTitle());
    }
}