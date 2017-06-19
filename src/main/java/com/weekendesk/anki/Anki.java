package com.weekendesk.anki;

public class Anki {
    private final CardFile cardFile;

    public Anki(final CardFile cardFile) {
        this.cardFile = cardFile;
    }

    public void start() {
        final Session session = this.cardFile.read();
        session.start();
        if (session.isAllCardsInGreenBox()) {
            printCongratulationMessage();
            deleteSaveSessionFile();
        } else {
            session.moveCardsForNextSession();
            save(session);
            printGoodbyMessage();
        }
    }

    private void printCongratulationMessage() {
        System.out.println("All cards are in green box. Congratulation!");
    }

    private void deleteSaveSessionFile() {
        this.cardFile.deleteSaveFile();
    }

    private void save(final Session session) {
        this.cardFile.save(session);
    }

    private void printGoodbyMessage() {
        System.out.println("Thank you! See you tomorrow!");
    }
}
