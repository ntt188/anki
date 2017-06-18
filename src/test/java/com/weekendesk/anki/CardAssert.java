package com.weekendesk.anki;

import org.junit.Assert;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CardAssert {
    private Card card;

    public static CardAssert assertThat(final Card card) {
        CardAssert cardAssert = new CardAssert();
        cardAssert.card = card;
        return cardAssert;
    }

    public CardAssert hasQuestion(final String question) {
        Assert.assertThat(card.getQuestion(), is(equalTo(question)));
        return this;
    }

    public CardAssert hasAnswer(final String answer) {
        Assert.assertThat(card.getAnswer(), is(equalTo(answer)));
        return this;
    }
}
