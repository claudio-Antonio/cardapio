package com.example.cardapio.services;

import com.example.cardapio.domain.UserIfood;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserIfoodServiceTest {
    @Mock
    private UserIfoodRepository userIfoodRepositoryMock;

    @InjectMocks
    private UserIfoodService userIfoodService;

    @BeforeEach
    void setUp() {
        BDDMockito.lenient().when(userIfoodRepositoryMock.findByUsername(ArgumentMatchers.anyString())).thenReturn(Creator.userCreator());
    }

    @Test
    @DisplayName("Should return user by username successfully from DB")
    void findByUsername() {
        UserIfood user = Creator.userCreator();

        UserDetails resultSearch = userIfoodService.loadUserByUsername(user.getUsername());

        Assertions.assertThat(resultSearch).isNotNull().isEqualTo(user);
    }
}
