package com.example.paymentDrink.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CardDTO {

    @Min(value = 0, message = "Le solde initial doit être supérieur ou égal à 0")
    private double initialBalance;

    @NotBlank(message = "Le nom du propriétaire est obligatoire")
    private String ownerName;
}
