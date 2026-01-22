package com.example.cardapio.repositories;

import com.example.cardapio.domain.Food;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class FoodRepositoryTest {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get food successfully from DB")
    void FindByTitleCase1(){
        Food newFood = foodCreator();

        Food resultSearch = foodRepository.findByTitle(newFood.getTitle());

        Assertions.assertThat(resultSearch).isNotNull();
    }

    @Test
    @DisplayName("Should not get food from DB when food not exists")
    void FindByTitleCase2() {
        String title = "Frango Frito";

        Food resultSearch = foodRepository.findByTitle(title);

        Assertions.assertThat(resultSearch).isNull();
    }

    @Test
    @DisplayName("Should save food successfully")
    void saveFood() {
        Food newFood = foodCreator();

        Food resultSearch = foodRepository.save(newFood);

        Assertions.assertThat(resultSearch).isNotNull();
        Assertions.assertThat(resultSearch.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should get list of foods successfully from DB")
    void findAllCase1(){
        Food newFood = foodCreator();

        foodRepository.save(newFood);

        List<Food> resultSearch = foodRepository.findAll();

        Assertions.assertThat(resultSearch).isNotEmpty().contains(newFood);
    }

    @Test
    @DisplayName("Should not get list of foods when database is empty")
    void findAllCase2(){
        List<Food> resultSearch = foodRepository.findAll();

        Assertions.assertThat(resultSearch).isEmpty();
    }

    /* --------------------------------------------------------------------------
    * Method which create Food Object*/
    public Food foodCreator() {
        Food food = Food.builder()
                .title("Frangnífico")
                .image("ablabla")
                .price(30)
                .build();

        entityManager.persist(food);
        return food;
    }

}
