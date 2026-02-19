package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    private String owner;
    private final List<String> cards;
    private final List<Double> cash;

    public Wallet() {
        this.cards = new ArrayList<>();
        this.cash = new ArrayList<>();
    }

    public Wallet(String owner) {
        this();
        setOwner(owner);
    }

    public void setOwner(String owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner tidak boleh null");
        }
        this.owner = owner;
    }

    public String getOwner() {
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
            if (amount == null) {
                throw new IllegalArgumentException("amount tidak boleh null");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("amount harus > 0");
            }
        }
        this.cash.addAll(cash);
    }

    public void withdrawCash(List<Double> cash) {
        if (cash == null) {
            throw new IllegalArgumentException("cash tidak boleh null");
        }
        for (Double amount : cash) {
            if (amount == null) {
                throw new IllegalArgumentException("amount tidak boleh null");
            }
            if (this.cash.contains(amount)) {
                this.cash.remove(amount);
            }
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
}
