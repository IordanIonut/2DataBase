package com.example.DataBase.Primary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;
    @NotNull(message = "Name")
    private String name;
    @NotNull(message = "Description")
    private String description;
    @NotNull(message = "Price")
    private Double price;
    @NotNull(message = "Stock")
    private Integer stock;
}
