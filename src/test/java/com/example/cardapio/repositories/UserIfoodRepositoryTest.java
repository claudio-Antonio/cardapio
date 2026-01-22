package com.example.cardapio.repositories;

import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.enums.UserIfoodRole;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserIfoodRepositoryTest {
    @Autowired
    private UserIfoodRepository userIfoodRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should return user successfully from DB")
    void findByUsernameCase1() {
        UserDetails newUser = createUser();

        UserDetails resultSearch = userIfoodRepository.findByUsername("claudio");

        Assertions.assertThat(resultSearch).isNotNull();
        Assertions.assertThat(resultSearch.getUsername()).isEqualTo("claudio");
    }

    /* --------------------------------------------------------------------------
     * Method which create UserIfood Object*/
    private UserDetails createUser() {
        UserIfood user = UserIfood.builder()
                .username("claudio")
                .password("{bcrypt}$2a$10$KKf.dh3Rjzpy75JKdD5IDegiNo5lonqQemqUyprfv3f4mba5Z9upC")
                .role(UserIfoodRole.ADMIN)
                .build();

        entityManager.persist(user);

        return user;
    }

}