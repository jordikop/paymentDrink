package com.example.paymentDrink.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Boisson boisson;

    private double amount; // Prix de la boisson

    private LocalDateTime timestamp;

    @ManyToOne
    private Card card;
}
