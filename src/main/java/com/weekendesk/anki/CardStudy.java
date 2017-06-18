package com.weekendesk.anki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class CardStudy {
    private final BufferedReader bufferedReader;

    public CardStudy() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void study(final Card card) {
        System.out.println("Question: " + card.getQuestion());
        System.out.println("Press enter to show the answer.");
        readInput();
        System.out.println("Answer: " + card.getAnswer());
        System.out.println("Please choose a box to put the card: (1) Red Box, (2) Orange Box, (3) Green Box");

        while (true) {
            System.out.println("Your answer (1|2|3): ");
            String userInput = readInput();
            Optional<Box> boxOptional = Box.fromInput(userInput);
            if (boxOptional.isPresent()) {
                boxOptional.get().put(card);
                break;
            } else {
                System.out.println("\n Invalid answer. Please input 1 or 2 or 3!");
            }
        }
    }

    private String readInput() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            closeBufferedReader();
            throw new RuntimeException("Read input error", e);
        }
    }

    public void end() {
        closeBufferedReader();
    }

    private void closeBufferedReader() {
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
