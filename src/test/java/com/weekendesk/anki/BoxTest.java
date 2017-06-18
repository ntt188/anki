package com.weekendesk.anki;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BoxTest {

    @Test
    public void tesFromInput() {
        assertThat(Box.fromInput("1").isPresent(), is(true));
        assertThat(Box.fromInput("1").get(), is(equalTo(Box.RED)));

        assertThat(Box.fromInput("2").isPresent(), is(true));
        assertThat(Box.fromInput("2").get(), is(equalTo(Box.ORANGE)));

        assertThat(Box.fromInput("3").isPresent(), is(true));
        assertThat(Box.fromInput("3").get(), is(equalTo(Box.GREEN)));

        assertThat(Box.fromInput("4").isPresent(), is(false));
        assertThat(Box.fromInput("a").isPresent(), is(false));
        assertThat(Box.fromInput("b").isPresent(), is(false));
        assertThat(Box.fromInput("c").isPresent(), is(false));
    }

    @Test
    public void testMoveCardsForNextSession() {
        Box.clearAll();
        Box.RED.put(new Card("Question 1", "Answer 1"));
        Box.ORANGE.put(new Card("Question 2", "Answer 2"));
        Box.GREEN.put(new Card("Question 3", "Answer 3"));

        Box.moveCardsForNextSession();

        assertThat(Box.RED.cards(), hasSize(2));
        CardAssert.assertThat(Box.RED.cardAt(0)).hasQuestion("Question 1");
        CardAssert.assertThat(Box.RED.cardAt(1)).hasQuestion("Question 2");

        assertThat(Box.ORANGE.cards(), hasSize(1));
        CardAssert.assertThat(Box.ORANGE.cardAt(0)).hasQuestion("Question 3");
    }
}