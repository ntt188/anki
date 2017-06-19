package com.weekendesk.anki;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class AnkiTest {

    private CardFile cardFile;
    private Session session;
    private Anki anki;

    @Before
    public void setUp() {
        cardFile = mock(CardFile.class);
        session = mock(Session.class);
        when(cardFile.read()).thenReturn(session);
        anki = new Anki(cardFile);
    }

    @Test
    public void testSessionEndedWithAllCardsInGreenBox() {
        when(session.isAllCardsInGreenBox()).thenReturn(true);

        anki.start();

        InOrder inOrder = inOrder(cardFile, session);
        inOrder.verify(cardFile).read();
        inOrder.verify(session).start();
        inOrder.verify(session).isAllCardsInGreenBox();
        inOrder.verify(cardFile).deleteSaveFile();
        Mockito.verifyNoMoreInteractions(cardFile, session);
    }

    @Test
    public void testSessionEndedWithAtLeastOneCardInRedBoxOrOrangeBox() {
        when(session.isAllCardsInGreenBox()).thenReturn(false);

        anki.start();

        InOrder inOrder = inOrder(cardFile, session);
        inOrder.verify(cardFile).read();
        inOrder.verify(session).start();
        inOrder.verify(session).isAllCardsInGreenBox();
        inOrder.verify(session).moveCardsForNextSession();
        inOrder.verify(cardFile).save(session);
        Mockito.verifyNoMoreInteractions(cardFile, session);
    }
}