package com.example.paymentDrink.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private int purchaseCount = 0;
}
