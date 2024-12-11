package com.example.paymentDrink.service;

import com.example.paymentDrink.model.Boisson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoissonService {

    public List<String> getBeveragesWithPrices(){
        return Arrays.stream(Boisson.values()).map(boisson -> boisson.name() +": $" + boisson.getPrice()).collect(Collectors.toList());
    }

}
