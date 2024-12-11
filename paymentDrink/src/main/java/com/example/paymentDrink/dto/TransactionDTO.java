package com.example.paymentDrink.dto;

import com.example.paymentDrink.model.Boisson;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TransactionDTO {

    private Long id;
    private Boisson boisson;
    private double amount;
    private LocalDateTime timestamp;
}
