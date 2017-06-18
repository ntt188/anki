package com.weekendesk.anki;

import java.io.File;

public final class Main {

    public static void main(String[] args) {
        if (saveFileExists()) {
            continueLastGame();
        } else {
            startNewGame();
        }
    }

    private static boolean saveFileExists() {
        return new File(CardFileConst.SAVE_FILE).exists();
    }

    private static void continueLastGame() {
        System.out.println("Start new session of last game!");
        startAnki(CardFileConst.SAVE_FILE);
    }

    private static void startAnki(final String cardFile) {
        Anki anki = new Anki(cardFile);
        anki.start();
    }

    private static void startNewGame() {
        System.out.println("Start new game!");
        startAnki(CardFileConst.ART_DECK_FILE);
    }
}
