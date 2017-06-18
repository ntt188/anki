package com.weekendesk.anki;

import java.util.Comparator;
import java.util.List;

public class CardSession {
    private CardStudy cardStudy;
    private List<Card> unstudiedCards;

    public CardSession(final List<Card> unstudiedCards) {
        this.unstudiedCards = unstudiedCards;
        this.cardStudy = new CardStudy();
    }

    public void start() {
        List<Card> cardsInRexBox = Box.RED.takeAllCards();
        this.study(this.unstudiedCards);
        this.study(cardsInRexBox);
        this.cardStudy.end();
    }

    private void study(final List<Card> cards) {
        cards.sort(Comparator.comparing(Card::getQuestion));
        cards.forEach(card -> cardStudy.study(card));
    }
}
