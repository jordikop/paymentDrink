package com.example.paymentDrink;

import com.example.paymentDrink.model.Boisson;
import com.example.paymentDrink.model.Card;
import com.example.paymentDrink.model.Transaction;
import com.example.paymentDrink.repository.CardRepository;
import com.example.paymentDrink.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.paymentDrink.service.BuissnessService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class serviceTestUnit {


    @Mock
    private CardRepository cardRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private BuissnessService service;

    @Test
    public void createCardTest() {

         Card card = new Card();
        card.setOwnerName("Jordi kerol");
        card.setBalance(100.0);

        when(cardRepository.save(any(Card.class))).thenReturn(card);
         Card result = service.createCard(card.getOwnerName(), card.getBalance());

        assertNotNull(result);
        assertEquals(card.getOwnerName(), result.getOwnerName());
        assertEquals(card.getBalance(), result.getBalance(), 1e-6);


        verify(cardRepository, times(1)).save(any(Card.class));


    }

    @Test
    public void recharge() {
        Card card = new Card();
        card.setId(1L);
        card.setOwnerName("Jordi kerol");
        card.setBalance(100.0);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);


        Card result = service.rechargeCard(1L, 20.0);

        // Assertions
        assertNotNull(result);
        assertEquals(120.0, result.getBalance(), 1e-6);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void  testPurchaseBeverageThrowsExceptionWhenInsufficientBalance() {

        Card card = new Card();
        card.setId(1L);
        card.setOwnerName("John Doe");
        card.setBalance(100.0); // Set initial balance
        card.setPurchaseCount(0);

        // Initialize Boisson
        Boisson tea = Boisson.TEA;

        card.setBalance(00.0); // Less than the price of the beverage
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.purchaseBeverage(1L, tea);
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));

    }

    @Test
    public void testPurchaseBeverageSuccess() {

        Card card = new Card();
        card.setId(1L);
        card.setOwnerName("John Doe");
        card.setBalance(100.0);
        Boisson tea = Boisson.TEA;
        card.setPurchaseCount(4);
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = service.purchaseBeverage(1L, tea);

        assertNotNull(transaction);
        assertEquals(tea.getPrice()/2, transaction.getAmount(), 1e-6);
        assertEquals(99.0, card.getBalance(), 1e-6);
        assertEquals(0, card.getPurchaseCount());
        verify(cardRepository, times(1)).save(card);
        verify(transactionRepository, times(1)).save(transaction);
    }

}
