package com.example.DataBase.Primary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@Table(name ="clients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_client;
    @NotNull(message = "Name")
    private String name;
    @NotNull(message = "Address")
    private String address;
    @NotNull(message = "Phone Number")
    private Integer phone_number;
}
