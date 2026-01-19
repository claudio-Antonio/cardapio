package com.example.cardapio.controller;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.dto.FoodRequestDTO;
import com.example.cardapio.domain.dto.FoodResponseDTO;
import com.example.cardapio.services.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("foods")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodResponseDTO>> getAll() {
        List<FoodResponseDTO> responseDTO = foodService.findAll().stream().map(FoodResponseDTO::new).toList();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<Void> saveFood(@RequestBody FoodRequestDTO requestDTO) {
        Food foodData = new Food(requestDTO);
        foodService.save(foodData);
        return ResponseEntity.ok().build();
    }

}
