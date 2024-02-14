package com.example.DataBase.Primary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name ="reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_review;
    @ManyToOne
    private Products id_product;
    @ManyToOne
    private Clients id_client;
    @NotNull(message = "Review")
    private String review;
}
