package com.example.DataBase.Secondary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_transaction;
    @NotNull(message = "Type")
    private String type;
    @NotNull(message = "Amount")
    private Double amount;
    @NotNull(message = "Date")
    private LocalDateTime date;
    @NotNull(message = "Details")
    private String details;

}
