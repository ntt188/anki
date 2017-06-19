package com.weekendesk.anki;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SessionTest {

    private final Card card1 = new Card("Question 1", "Answer 1");
    private final Card card2 = new Card("Question 2", "Answer 2");
    private final Card card3 = new Card("Question 3", "Answer 3");

    private CardStudy cardStudy;
    private Session session;

    @Before
    public void setUp() {
        cardStudy = mock(CardStudy.class);
        session = new Session(cardStudy);
    }

    @Test
    public void testFirstSession() {
        session.addUnstudiedCard(card3);
        session.addUnstudiedCard(card1);
        session.addUnstudiedCard(card2);
        when(cardStudy.study(card1)).thenReturn(Color.ORANGE);
        when(cardStudy.study(card2)).thenReturn(Color.RED).thenReturn(Color.ORANGE);
        when(cardStudy.study(card3)).thenReturn(Color.GREEN);

        session.start();

        assertThat(session.getRedBox().cards(), is(empty()));

        assertThat(session.getOrangeBox().cards(), hasSize(2));
        assertThat(session.getOrangeBox().cardAt(0), is(card1));
        assertThat(session.getOrangeBox().cardAt(1), is(card2));

        assertThat(session.getGreenBox().cards(), is(hasSize(1)));
        assertThat(session.getGreenBox().cardAt(0), is(card3));

        InOrder inOrder = inOrder(cardStudy);
        inOrder.verify(cardStudy).initialize();
        inOrder.verify(cardStudy).study(card1);
        inOrder.verify(cardStudy).study(card2);
        inOrder.verify(cardStudy).study(card3);
        inOrder.verify(cardStudy).study(card2);
        inOrder.verify(cardStudy).end();
        verifyNoMoreInteractions(cardStudy);
    }

    @Test
    public void testNewSessionOfLastGame() {
        session.getRedBox().put(card2);
        session.getRedBox().put(card1);
        session.getOrangeBox().put(card3);

        when(cardStudy.study(card1)).thenReturn(Color.ORANGE);
        when(cardStudy.study(card2)).thenReturn(Color.GREEN);

        session.start();

        assertThat(session.getRedBox().cards(), is(empty()));

        assertThat(session.getOrangeBox().cards(), hasSize(2));
        assertThat(session.getOrangeBox().cardAt(0), is(card3));
        assertThat(session.getOrangeBox().cardAt(1), is(card1));

        assertThat(session.getGreenBox().cards(), is(hasSize(1)));
        assertThat(session.getGreenBox().cardAt(0), is(card2));

        InOrder inOrder = inOrder(cardStudy);
        inOrder.verify(cardStudy).initialize();
        inOrder.verify(cardStudy).study(card2);
        inOrder.verify(cardStudy).study(card1);
        inOrder.verify(cardStudy).end();
        verifyNoMoreInteractions(cardStudy);
    }

    @Test
    public void testMoveCardsForNextSession() {
        session.getOrangeBox().put(card1);
        session.getOrangeBox().put(card2);
        session.getGreenBox().put(card3);

        session.moveCardsForNextSession();

        assertThat(session.getRedBox().cards(), hasSize(2));
        assertThat(session.getRedBox().cardAt(0), is(card1));
        assertThat(session.getRedBox().cardAt(1), is(card2));

        assertThat(session.getOrangeBox().cards(), hasSize(1));
        assertThat(session.getOrangeBox().cardAt(0), is(card3));

        assertThat(session.getGreenBox().cards(), is(empty()));
    }
}