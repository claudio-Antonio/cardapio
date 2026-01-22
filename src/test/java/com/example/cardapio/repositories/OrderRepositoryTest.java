package com.example.cardapio.repositories;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.enums.StatusRequest;
import com.example.cardapio.domain.enums.UserIfoodRole;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return list of orders successfully from DB")
    void findAllCase1() {
        Order newOrder = createOrder();

        orderRepository.save(newOrder);

        List<Order> resultSearch = orderRepository.findAll();

        Assertions.assertThat(resultSearch).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should not return list of orders from DB")
    void findAllCase2() {
        List<Order> resultSearch = orderRepository.findAll();

        Assertions.assertThat(resultSearch).isEmpty();
    }

    @Test
    @DisplayName("Should find order by id successfully")
    void findByIdCase1() {
        Order order = createOrder();

        Optional<Order> resultSearch = orderRepository.findById(order.getId());

        Assertions.assertThat(resultSearch.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not find order by id")
    void findByIdCase2() {
        Optional<Order> resultSearch = orderRepository.findById(4L);

        Assertions.assertThat(resultSearch.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should save order successfully in DB")
    void save() {
        Order order = createOrder();

        Order result = orderRepository.save(order);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should delete order obj successfully")
    void deleteById() {
        Order order = createOrder();

        Order save = orderRepository.save(order);

        orderRepository.delete(save);

        Optional<Order> resultSearch = orderRepository.findById(order.getId());

        Assertions.assertThat(resultSearch.isEmpty()).isTrue();
    }

    /* --------------------------------------------------------------------------
     * Method which create Food Object*/
    public List<Food> foodCreator() {
        List<Food> foods = new ArrayList<>();

        Food food = Food.builder()
                .title("Frangnífico")
                .image("ablabla")
                .price(30)
                .build();

        entityManager.persist(food);
        foods.add(food);

        return foods;
    }

    /* --------------------------------------------------------------------------
     * Method which create UserIfood Object*/
    private UserIfood createUser() {
        UserIfood user = UserIfood.builder()
                .username("claudio")
                .password("{bcrypt}$2a$10$KKf.dh3Rjzpy75JKdD5IDegiNo5lonqQemqUyprfv3f4mba5Z9upC")
                .role(UserIfoodRole.ADMIN)
                .build();

        entityManager.persist(user);

        return user;
    }

    /* --------------------------------------------------------------------------
     * Method which create Order Object*/
    private Order createOrder() {
        List<Food> foods = foodCreator();
        UserIfood user = createUser();

        Order order = Order.builder()
                .dataOrder(LocalDateTime.of(2026, 1, 31, 2, 55, 4))
                .status(StatusRequest.EM_PREPARO)
                .total(25.0)
                .user(user)
                .foods(foods)
                .build();

        entityManager.persist(order);

        return order;
    }
}