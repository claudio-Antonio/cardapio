package com.example.cardapio.services;

import com.example.cardapio.domain.Order;
import com.example.cardapio.repositories.OrderRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        BDDMockito.lenient().when(orderRepositoryMock.findAll()).thenReturn(List.of(Creator.orderCreator()));

        BDDMockito.lenient().when(orderRepositoryMock.save(ArgumentMatchers.any(Order.class))).thenReturn(Creator.orderCreator());

        BDDMockito.lenient().when(orderRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(Creator.orderCreator()));

        BDDMockito.lenient().doNothing().when(orderRepositoryMock).delete(ArgumentMatchers.any(Order.class));
    }

    @Test
    @DisplayName("Should return list successfully")
    void findAll() {
        List<Order> orders = orderService.findAll();

        Assertions.assertThat(orders).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should save order successfully")
    void save() {
        Order order = Creator.orderCreator();

        Order saved = orderService.save(order);

        Assertions.assertThat(saved).isNotNull().isEqualTo(order);
    }

    @Test
    @DisplayName("Should return order successfully")
    void findByIdCase1() {
        Order order = orderService.findById(1L);

        Assertions.assertThat(order).isNotNull();
    }

    @Test
    @DisplayName("Should delete order successfully")
    void delete() {
        Order order = Creator.orderCreator();

        Assertions.assertThatCode(() -> orderService.delete(order)).doesNotThrowAnyException();

    }
}