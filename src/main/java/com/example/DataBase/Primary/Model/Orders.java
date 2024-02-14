package com.example.DataBase.Primary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order;
    @ManyToOne
    private Clients id_client;
    @NotNull(message = "Price")
    private Double price;
    @NotNull(message = "Quantity")
    private Integer quantity;
    @NotNull(message = "Total Price")
    private Double total_price;
}
