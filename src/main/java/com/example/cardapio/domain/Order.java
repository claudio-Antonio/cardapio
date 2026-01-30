package com.example.cardapio.domain;

import com.example.cardapio.domain.dto.OrderRequestDTO;
import com.example.cardapio.domain.enums.StatusRequest;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataOrder;

    @Enumerated(EnumType.ORDINAL)
    private StatusRequest status;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "user_id",  nullable = false)
    private UserIfood user;

    @ManyToMany
    @JoinTable(name = "order_foods",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> foods = new ArrayList<>();

    public Order(OrderRequestDTO request) {
        dataOrder = request.dataOrder();
        status = request.status();
        total = request.total();
        foods = request.food();
    }
}
