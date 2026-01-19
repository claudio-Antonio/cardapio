package com.example.cardapio.domain;

import com.example.cardapio.domain.dto.FoodRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "foods")
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String image;
    private Integer price;

    public Food(FoodRequestDTO request) {
        this.title = request.title();
        this.image = request.image();
        this.price = request.price();
    }
}
