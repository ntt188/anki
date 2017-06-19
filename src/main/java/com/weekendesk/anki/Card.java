package com.weekendesk.anki;

public class Card {
    private String question;
    private String answer;

    public Card() {
    }

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

    public void setQuestion(final String question) {
        this.question = question;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }
}
