package com.example.DataBase.Secondary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name="financialReports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_report;
    @NotNull(message = "Type")
    private String type;
    @NotNull(message = "Date")
    private LocalDate date;
    @NotNull(message = "Details")
    private String details;
}
