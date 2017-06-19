package com.weekendesk.anki;

import java.util.ArrayList;
import java.util.List;

public class Box {

    private final Color color;
    private final List<Card> cards;

    Box(final Color color) {
        this.color = color;
        this.cards = new ArrayList<>();
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

    public Color getColor() {
        return color;
    }
}
