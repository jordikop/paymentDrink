package com.example.paymentDrink.service;

import com.example.paymentDrink.model.Boisson;
import com.example.paymentDrink.model.Card;
import com.example.paymentDrink.model.Transaction;
import com.example.paymentDrink.dto.TransactionDTO;
import com.example.paymentDrink.repository.CardRepository;
import com.example.paymentDrink.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuissnessService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Méthode pour acheter une boisson
    public Transaction purchaseBeverage (Long cardId, Boisson boisson) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        double price = boisson.getPrice();

        if(card.getPurchaseCount()==4){
            price *= 0.5; // 50 % de réduction
            card.setPurchaseCount(0);
        }
        else {
            card.setPurchaseCount(card.getPurchaseCount() + 1);
        }

        if (card.getBalance() < price) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        card.setBalance(card.getBalance() - price);
        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setBoisson(boisson);
        transaction.setAmount(price);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setCard(card);

        return transactionRepository.save(transaction);
    }

    public Card rechargeCard(Long cardId, double amount) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        card.setBalance(card.getBalance() + amount);
        return cardRepository.save(card);
    }

    public List<TransactionDTO> getTransactionsByCard(Long cardId) {
        return transactionRepository.findByCardId(cardId).stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setId(transaction.getId());
                    dto.setBoisson(transaction.getBoisson());
                    dto.setAmount(transaction.getAmount());
                    dto.setTimestamp(transaction.getTimestamp());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Card createCard(String ownerName, double initialBalance) {
        Card card = new Card();
        card.setOwnerName(ownerName);
        card.setBalance(initialBalance);
        return cardRepository.save(card);
    }
    // Méthode pour obtenir les boissons les plus achetées
    public Map<Boisson, Long> getMostPurchasedBeverages() {
        List<Object[]> results = transactionRepository.findMostPurchasedBeverages();
        Map<Boisson, Long> mostPurchased = new LinkedHashMap<>();

        for (Object[] result : results) {
            Boisson beverage = (Boisson) result[0];
            Long count = (Long) result[1];
            mostPurchased.put(beverage, count);
        }

        return mostPurchased;
    }
}