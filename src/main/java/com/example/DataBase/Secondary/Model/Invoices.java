package com.example.DataBase.Secondary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name ="invoices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_invoice;
    @NotNull(message = "Number")
    private Integer number;
    @NotNull(message = "Date")
    private LocalDateTime date;
    @NotNull(message = "Amount")
    private Double amount;
    @NotNull(message = "Details")
    private String details;
}
