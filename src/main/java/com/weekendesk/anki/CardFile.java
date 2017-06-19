package com.weekendesk.anki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CardFile {
    private static final String ART_DECK_FILE = "deck/art.txt";
    static final String SAVE_FILE = "save/save.txt";

    public Session read() {
        if (saveFileExists()) {
            return readFile(SAVE_FILE);
        }

        return readFile(ART_DECK_FILE);
    }

    private boolean saveFileExists() {
        return new File(SAVE_FILE).exists();
    }

    private Session readFile(final String file) {
        Session session = new Session(new CardStudy());

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.skip(1).forEach(line -> readCard(line, session));
        } catch (IOException e) {
            throw new RuntimeException("Read file error", e);
        }

        return session;
    }

    private void readCard(final String line, final Session session) {
        final String[] parts = line.split("\\|");

        if (parts.length == 2) {
            session.addUnstudiedCard(new Card(parts[0], parts[1]));
        } else if (parts.length == 3) {
            Color color = Color.valueOf(parts[2]);
            session.addCard(new Card(parts[0], parts[1]), color);
        } else {
            throw new IllegalStateException("Invalid line format: " + line);
        }
    }

    public void save(final Session session) {
        try (FileWriter writer = new FileWriter(SAVE_FILE, false)) {
            writer.write(buildFileContent(session));
        } catch (IOException e) {
            throw new RuntimeException("Write file error", e);
        }
    }

    String buildFileContent(final Session session) {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("card question|card answer|box");
        appendCards(fileContent, session.getRedBox());
        appendCards(fileContent, session.getOrangeBox());
        return fileContent.toString();
    }

    private void appendCards(final StringBuilder fileContent, final Box box) {
        box.cards().forEach(card -> fileContent.append("\n").append(card.getQuestion())
                .append("|").append(card.getAnswer())
                .append("|").append(box.getColor().name()));
    }

    public void deleteSaveFile() {
        File saveFile = new File(SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}
