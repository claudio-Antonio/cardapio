package com.example.cardapio.controller;

import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.dto.AuthenticationDTO;
import com.example.cardapio.domain.dto.RegisterDTO;
import com.example.cardapio.domain.dto.UserResponseDTO;
import com.example.cardapio.repositories.UserIfoodRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserIfoodRepository  userRepository;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (UserIfood) auth.getPrincipal();

        return ResponseEntity.ok().body(new UserResponseDTO(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterDTO data) {
        if(userRepository.findByUsername(data.username()) != null)
            return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserIfood user = new UserIfood(data.username(), encryptedPassword, data.role());

        userRepository.save(user);

        return ResponseEntity.ok().body(new UserResponseDTO(user));
    }
}
