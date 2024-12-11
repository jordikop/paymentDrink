package com.example.paymentDrink.model;

public enum Boisson {

    COFFEE(2.50),
    TEA(2.00),
    SODA(1.50),
    WATER(1.00),
    JUICE(3.00);

    private final double price;

    Boisson(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
}
