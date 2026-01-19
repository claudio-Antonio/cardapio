package com.example.cardapio.domain.dto;

import com.example.cardapio.domain.UserIfoodRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(String username, String password, @NotNull UserIfoodRole role) {
}
