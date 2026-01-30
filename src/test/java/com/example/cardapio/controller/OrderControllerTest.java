package com.example.cardapio.controller;

import com.example.cardapio.domain.Order;
import com.example.cardapio.domain.dto.OrderRequestDTO;
import com.example.cardapio.domain.dto.OrderResponseDTO;
import com.example.cardapio.services.OrderService;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    private OrderService orderServiceMock;

    @InjectMocks
    private OrderController orderController;

    private final OrderRequestDTO orderRequestDTO = new OrderRequestDTO(Creator.orderCreator());

    private final OrderResponseDTO orderResponseDTO = new OrderResponseDTO(Creator.orderCreator());

    @BeforeEach
    void setUp() {
        List<Order> orders = List.of(Creator.orderCreator());

        BDDMockito.lenient().when(orderServiceMock.findAll()).thenReturn(orders);

        BDDMockito.lenient().when(orderServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(Creator.orderCreator());

        BDDMockito.lenient().when(orderServiceMock.save(ArgumentMatchers.any(Order.class))).thenReturn(Creator.orderCreator());

        BDDMockito.lenient().doNothing().when(orderServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Should return a list of orders successfully")
    void getAll() {
        ResponseEntity<List<OrderResponseDTO>> list = orderController.findAll();

        Assertions.assertThat(list.getBody()).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should return order by id successfully")
    void findById() {
        Order order  = Creator.orderCreator();

        HttpEntity<OrderResponseDTO> resultSearch = orderController.findById(order.getId());

        Assertions.assertThat(resultSearch.getBody()).isNotNull();
        Assertions.assertThat(resultSearch.getBody().id()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("Should save order successfully")
    void save() {
        HttpEntity<OrderResponseDTO> save = orderController.saveNewOrder(orderRequestDTO);

        Assertions.assertThat(save.getBody()).isNotNull().isEqualTo(orderResponseDTO);
    }

    @Test
    @DisplayName("Should delete order successfully")
    void delete() {
        Order order = Creator.orderCreator();

        Assertions.assertThatCode(() -> orderController.deleteOrder(order.getId())).doesNotThrowAnyException();
    }
}
