package com.example.DataBase.Secondary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name ="budgets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Budgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_budget;
    @NotNull(message = "Available")
    private Double available;
    @NotNull(message = "Anticipated")
    private Double anticipated;
}
