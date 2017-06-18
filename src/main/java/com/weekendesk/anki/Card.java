package com.weekendesk.anki;

public class Card {
    private final String question;
    private final String answer;

    public Card(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
