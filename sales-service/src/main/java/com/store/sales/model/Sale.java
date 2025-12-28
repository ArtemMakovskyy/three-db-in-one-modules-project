package com.store.sales.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private Double amount;
}
