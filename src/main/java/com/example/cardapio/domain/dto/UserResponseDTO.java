package com.example.cardapio.domain.dto;

import com.example.cardapio.domain.UserIfood;

public record UserResponseDTO(Long id, String username, String role) {
    public UserResponseDTO(UserIfood u) {
        this(u.getId(), u.getUsername(), u.getRole().name());
    }
}
