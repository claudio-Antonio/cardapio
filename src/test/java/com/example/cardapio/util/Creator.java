package com.example.cardapio.util;

import com.example.cardapio.domain.Food;
import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.UserIfood;
import com.example.cardapio.domain.enums.StatusRequest;
import com.example.cardapio.domain.enums.UserIfoodRole;

import java.time.LocalDateTime;
import java.util.List;

public class Creator {
    public static Food foodCreator() {
        Food food = Food.builder()
                .title("Frangnífico")
                .image("ablabla")
                .price(30)
                .build();

        return food;
    }

    public static UserIfood userCreator() {
        UserIfood user = UserIfood.builder()
                .username("claudio")
                .password("{bcrypt}$2a$10$KKf.dh3Rjzpy75JKdD5IDegiNo5lonqQemqUyprfv3f4mba5Z9upC")
                .role(UserIfoodRole.ADMIN)
                .build();

        return user;
    }

    public static Order orderCreator() {
        List<Food> foods = List.of(foodCreator());
        UserIfood user = userCreator();

        Order order = Order.builder()
                .id(1L)
                .dataOrder(LocalDateTime.of(2026, 1, 31, 2, 55, 4))
                .status(StatusRequest.EM_PREPARO)
                .total(25.0)
                .user(user)
                .foods(foods)
                .build();

        return order;
    }

}
