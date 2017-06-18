package com.weekendesk.anki;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CardFileReader {
    private final String cardFileName;
    private List<Card> unstudiedCards;

    public CardFileReader(final String cardFileName) {
        this.cardFileName = cardFileName;
    }

    public void read() {
        Box.clearAll();
        unstudiedCards = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(cardFileName))) {
            stream.skip(1).forEach(this::readCard);
        } catch (IOException e) {
            throw new RuntimeException("Read file error", e);
        }
    }

    private void readCard(final String line) {
        final String[] cardParts = line.split("\\|");

        final Card card = new Card(cardParts[0], cardParts[1]);
        if (cardParts.length == 2) {
            this.unstudiedCards.add(card);
        } else {
            Box box = Box.valueOf(cardParts[2]);
            box.put(card);
        }
    }

    public List<Card> getUnstudiedCards() {
        return unstudiedCards;
    }
}
