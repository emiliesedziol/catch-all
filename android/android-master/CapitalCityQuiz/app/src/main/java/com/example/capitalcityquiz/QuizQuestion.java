package com.example.capitalcityquiz;

public class QuizQuestion {

    private String mQuestion; // question displayed to the user
    private boolean mAnswerIsYes;

    QuizQuestion(String question,  boolean answer) {
        mQuestion = question;
        mAnswerIsYes = answer;
    }

    String getQuestion() {
        return mQuestion;
    }

    boolean getAnswer() {
        return mAnswerIsYes;
    }
}
