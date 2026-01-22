package com.example.cardapio.domain;

import com.example.cardapio.domain.dto.FoodRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "foods")
@Table(name = "tb_food")
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String image;
    private Integer price;

    @ManyToMany(mappedBy = "foods")
    private List<Order> list = new ArrayList<>();

    public Food(FoodRequestDTO request) {
        this.title = request.title();
        this.image = request.image();
        this.price = request.price();
    }
}
