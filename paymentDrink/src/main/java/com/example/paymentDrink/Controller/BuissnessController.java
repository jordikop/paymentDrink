package com.example.paymentDrink.Controller;

import com.example.paymentDrink.dto.CardDTO;
import com.example.paymentDrink.model.Boisson;
import com.example.paymentDrink.model.Card;
import com.example.paymentDrink.model.Transaction;
import com.example.paymentDrink.dto.TransactionDTO;
import com.example.paymentDrink.service.BoissonService;
import com.example.paymentDrink.service.BuissnessService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vending-machine")
public class BuissnessController {

    @Autowired
    private BuissnessService buissnessService;

    @Autowired
    private BoissonService boissonService;

    @PostMapping("/purchase")
    public Transaction purchaseBeverage(
            @RequestParam Long cardId,
            @RequestParam Boisson boisson) {
        return buissnessService.purchaseBeverage(cardId, boisson);
    }
    @PostMapping("/recharge")
    public Card rechargeCard(@RequestParam Long cardId, @RequestParam double amount) {
        return buissnessService.rechargeCard(cardId, amount);
    }
    @GetMapping("/beverages")
    public List<String> getBeveragesWithPrices() {
        return boissonService.getBeveragesWithPrices();
    }
    @GetMapping("/transactionById")
    public List<TransactionDTO> getTransactionById(@RequestParam Long cardId) {
        return buissnessService.getTransactionsByCard(cardId);
    }
    @PostMapping("/create-card")
    public Card createCard(@Valid @RequestBody CardDTO cardDTO) {
        return buissnessService.createCard(cardDTO.getOwnerName(), cardDTO.getInitialBalance());
    }

    // Endpoint pour obtenir les boissons les plus achetées
    @GetMapping("/most-purchased")
    public Map<Boisson, Long> getMostPurchasedBeverages() {
        return buissnessService.getMostPurchasedBeverages();
    }
}
