package com.blackjack;

public enum Suits {
    CLUB("Clubs"),
    DIAMOND("Diamonds"),
    HEART("Hearts"),
    SPADE("Spades");

    final String suitName;

    Suits(String suitName) {
        this.suitName = suitName;
    }

    public String toString(){
        return suitName;
    }
}