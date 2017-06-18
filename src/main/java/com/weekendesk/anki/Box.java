package com.weekendesk.anki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Box {
    RED("1"),
    ORANGE("2"),
    GREEN("3");

    private final String input;
    private final List<Card> cards;

    Box(final String input) {
        this.input = input;
        this.cards = new ArrayList<>();
    }

    public static void clearAll() {
        Arrays.stream(Box.values()).forEach(Box::clear);
    }

    public static Optional<Box> fromInput(final String input) {
        return Arrays.stream(Box.values()).filter(box -> box.input.equals(input)).findFirst();
    }

    public static boolean isAllCardsInGreenBox() {
        return Box.RED.isEmpty() && Box.ORANGE.isEmpty();
    }

    public static void moveCardsForNextSession() {
        moveAll(Box.ORANGE, Box.RED);
        moveAll(Box.GREEN, Box.ORANGE);
    }

    private static void moveAll(Box fromBox, Box targetBox) {
        targetBox.addAll(fromBox.cards());
        fromBox.clear();
    }

    public void put(final Card card) {
        this.cards.add(card);
    }

    public List<Card> cards() {
        return this.cards;
    }

    public void clear() {
        this.cards.clear();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void addAll(final List<Card> cards) {
        this.cards().addAll(cards);
    }

    public List<Card> takeAllCards() {
        List<Card> result = new ArrayList<>();
        result.addAll(this.cards());
        this.clear();
        return result;
    }

    public Card cardAt(final int index) {
        return this.cards().get(index);
    }
}
