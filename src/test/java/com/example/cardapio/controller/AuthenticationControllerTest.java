package com.example.cardapio.controller;

import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.dto.AuthenticationDTO;
import com.example.cardapio.domain.dto.RegisterDTO;
import com.example.cardapio.domain.dto.UserResponseDTO;
import com.example.cardapio.domain.enums.UserIfoodRole;
import com.example.cardapio.repositories.UserIfoodRepository;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationControllerTest {
    @Mock
    private UserIfoodRepository userIfoodRepositoryMock;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @InjectMocks
    private AuthenticationController authenticationController;

    private final AuthenticationDTO authenticationDTO = new AuthenticationDTO("Claudia", "123");
    private final RegisterDTO registerDTO = new RegisterDTO("Claudio", "123", UserIfoodRole.valueOf("ADMIN"));

    @BeforeEach
    void setUp() {
        UserIfood user = Creator.userCreator();
        Authentication auth = mock(Authentication.class);

        BDDMockito.given(authenticationManagerMock.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).willReturn(auth);

        BDDMockito.given(auth.getPrincipal()).willReturn(user);

        BDDMockito.given(userIfoodRepositoryMock.findByUsername("Claudio")).willReturn(null);

        UserIfood existingUser = new UserIfood();
        BDDMockito.given(userIfoodRepositoryMock.findByUsername("Mauro")).willReturn(existingUser);

        BDDMockito.given(userIfoodRepositoryMock.save(ArgumentMatchers.any(UserIfood.class))).willReturn(mock(UserIfood.class));
    }

    @Test
    @DisplayName("Should authenticate user successfully")
    void login() {
        ResponseEntity<UserResponseDTO> response = authenticationController.login(authenticationDTO);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("Should return OK when registered user successfully")
    void registerCase1() {
        ResponseEntity<UserResponseDTO> response = authenticationController.register(registerDTO);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    @DisplayName("Should return BAD REQUEST when user already exists")
    void registerCase2() {
        RegisterDTO request = new RegisterDTO("Mauro", "123", UserIfoodRole.valueOf("USER"));

        ResponseEntity<UserResponseDTO> response = authenticationController.register(request);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
}