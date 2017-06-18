package com.weekendesk.anki;

import java.util.List;

public class Anki {
    private final String cardFileName;

    public Anki(final String cardFileName) {
        this.cardFileName = cardFileName;
    }

    public void start() {
        List<Card> unstudiedCards = readCardsFromFile();
        startNewCardSession(unstudiedCards);
        if (Box.isAllCardsInGreenBox()) {
            printCongratulationMessage();
        } else {
            Box.moveCardsForNextSession();
            saveState();
            printGoodbyMessage();
        }
    }

    private List<Card> readCardsFromFile() {
        CardFileReader reader = new CardFileReader(cardFileName);
        reader.read();
        return reader.getUnstudiedCards();
    }

    private void startNewCardSession(final List<Card> unstudiedCards) {
        CardSession cardSession = new CardSession(unstudiedCards);
        cardSession.start();
    }

    private void printCongratulationMessage() {
        System.out.println("All cards are in green box. Congratulation!");
        new StateWriter().deleteSaveFile();
    }

    private void saveState() {
        new StateWriter().write();
    }

    private void printGoodbyMessage() {
        System.out.println("Thank you! See you tomorrow!");
    }
}
