package com.weekendesk.anki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class CardStudy {
    private BufferedReader bufferedReader;

    public void initialize() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Color study(final Card card) {
        System.out.println("Question: " + card.getQuestion());
        System.out.println("Press enter to show the answer.");
        readInput();
        System.out.println("Answer: " + card.getAnswer());
        System.out.println("Please choose a box to put the card: (1) Red Box, (2) Orange Box, (3) Green Box");

        while (true) {
            System.out.print("Your answer (1|2|3): ");
            String userInput = readInput();
            Optional<Color> colorOptional = getColor(userInput);
            if (colorOptional.isPresent()) {
                return colorOptional.get();
            }

            System.out.println("\n Invalid answer. Please press 1 or 2 or 3!");
        }
    }

    Optional<Color> getColor(final String userInput) {
        switch (userInput) {
            case "1":
                return Optional.of(Color.RED);
            case "2":
                return Optional.of(Color.ORANGE);
            case "3":
                return Optional.of(Color.GREEN);
            default:
                return Optional.empty();
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
