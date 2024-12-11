package com.example.paymentDrink.repository;

import com.example.paymentDrink.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCardId(Long cardId);
    @Query("SELECT t.boisson, COUNT(t) AS purchaseCount " +
            "FROM Transaction t " +
            "GROUP BY t.boisson " +
            "ORDER BY purchaseCount DESC")
    List<Object[]> findMostPurchasedBeverages();
}
