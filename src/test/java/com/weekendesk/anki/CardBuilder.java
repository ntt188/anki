package com.weekendesk.anki;

public class CardBuilder {
    private Card card;

    public static CardBuilder aCard() {
        CardBuilder builder = new CardBuilder();
        builder.card = new Card();
        return builder;
    }

    public CardBuilder withQuestion(final String question) {
        this.card.setQuestion(question);
        return this;
    }

    public CardBuilder withAnswer(final String answer) {
        this.card.setAnswer(answer);
        return this;
    }

    public Card build() {
        return this.card;
    }
}
