package com.weekendesk.anki;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StateWriter {

    public void write() {
        try (FileWriter writer = new FileWriter(CardFileConst.SAVE_FILE, false)) {
            writer.write(buildFileContent());
        } catch (IOException e) {
            throw new RuntimeException("Write file error", e);
        }
    }

    String buildFileContent() {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append("card question|card answer|box");
        appendCards(fileContent, Box.RED);
        appendCards(fileContent, Box.ORANGE);
        return fileContent.toString();
    }

    private void appendCards(final StringBuilder sb, final Box box) {
        for (Card card : box.cards()) {
            sb.append("\n").append(card.getQuestion())
                    .append("|").append(card.getAnswer())
                    .append("|").append(box.name());
        }
    }

    public void deleteSaveFile() {
        File saveFile = new File(CardFileConst.SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}
