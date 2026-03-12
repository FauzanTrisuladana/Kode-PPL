package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    private Owner owner;
    private final List<String> cards;
    private final List<Double> cash;

    public Wallet() {
        this.cards = new ArrayList<>();
        this.cash = new ArrayList<>();
    }

    public Wallet(Owner owner) {
        this();
        setOwner(owner);
    }

    public void setOwner(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner tidak boleh null");
        }
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void addCards(List<String> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("cards tidak boleh null");
        }
        for (String card : cards) {
            if (card == null) {
                throw new IllegalArgumentException("card tidak boleh null");
            }
        }
        this.cards.addAll(cards);
    }

    public void withdrawCards(List<String> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("cards tidak boleh null");
        }
        for (String card : cards) {
            if (card == null) {
                throw new IllegalArgumentException("card tidak boleh null");
            }
            if (this.cards.contains(card)) {
                this.cards.remove(card);
            }
        }
    }

    public List<String> getCards() {
        return Collections.unmodifiableList(new ArrayList<>(cards));
    }

    public void addCash(List<Double> cash) {
        if (cash == null) {
            throw new IllegalArgumentException("cash tidak boleh null");
        }
        for (Double amount : cash) {
            validateCashAmount(amount);
        }
        this.cash.addAll(cash);
    }

    public void addCash(double amount) {
        validateCashAmount(amount);
        cash.add(amount);
    }

    public void withdrawCash(List<Double> cash) {
        if (cash == null) {
            throw new IllegalArgumentException("cash tidak boleh null");
        }
        for (Double amount : cash) {
            withdrawCash(requireAmount(amount));
        }
    }

    public void withdrawCash(double amount) {
        validateCashAmount(amount);
        double remaining = getCashAmount() - amount;
        if (remaining < 0) {
            throw new InsufficientFundsException("saldo tidak cukup");
        }

        cash.clear();
        if (remaining > 0) {
            cash.add(remaining);
        }
    }

    public List<Double> getCash() {
        return Collections.unmodifiableList(new ArrayList<>(cash));
    }

    public double getCashAmount() {
        double total = 0;
        for (Double amount : cash) {
            total += amount;
        }
        return total;
    }

    private void validateCashAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount harus > 0");
        }
    }

    private double requireAmount(Double amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount tidak boleh null");
        }
        return amount;
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
