package com.weekendesk.anki;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Session {
    private CardStudy cardStudy;
    private final Box redBox;
    private final Box orangeBox;
    private final Box greenBox;
    private List<Card> unstudiedCards;

    public Session(final CardStudy cardStudy) {
        this.cardStudy = cardStudy;

        this.unstudiedCards = new ArrayList<>();
        this.redBox = new Box(Color.RED);
        this.orangeBox = new Box(Color.ORANGE);
        this.greenBox = new Box(Color.GREEN);
    }

    public void start() {
        this.cardStudy.initialize();
        this.unstudiedCards.sort(Comparator.comparing(Card::getQuestion));
        this.study(this.unstudiedCards);
        List<Card> cardsInRexBox = redBox.takeAllCards();
        this.study(cardsInRexBox);
        this.cardStudy.end();
    }

    private void study(final List<Card> cards) {
        cards.forEach(this::study);
    }

    private void study(final Card card) {
        Color color = cardStudy.study(card);
        addCard(card, color);
    }

    public void addCard(final Card card, final Color color) {
        Box box = findBox(color);
        box.put(card);
    }

    private Box findBox(final Color color) {
        switch (color) {
            case RED:
                return redBox;
            case ORANGE:
                return orangeBox;
            case GREEN:
                return greenBox;
            default:
                throw new IllegalStateException("Box not found for color " + color.name());
        }
    }

    List<Card> getUnstudiedCards() {
        return unstudiedCards;
    }

    public void addUnstudiedCard(final Card card) {
        this.unstudiedCards.add(card);
    }

    public boolean isAllCardsInGreenBox() {
        return redBox.isEmpty() && orangeBox.isEmpty();
    }

    public void moveCardsForNextSession() {
        redBox.addAll(orangeBox.cards());
        orangeBox.clear();
        orangeBox.addAll(greenBox.cards());
        greenBox.clear();
    }

    public Box getRedBox() {
        return redBox;
    }

    public Box getOrangeBox() {
        return orangeBox;
    }

    public Box getGreenBox() {
        return greenBox;
    }
}
