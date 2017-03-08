package com.example.stamatis.questiongameapp.game_util;

/**
 * Created by Stamatis Stiliatis(ExXoDuSs) on 17/2/2017.
 */

public class Questions {

    private String mQuestion;
    private String[] mAnswers = new String[4];
    private int mCorrectAnswer;
    private boolean mAlreadyDisplayed;

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public int getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(int mCorrectAnswer) {
        this.mCorrectAnswer = mCorrectAnswer;
    }

    public String getAnswers(int index) {
        return mAnswers[index];
    }

    // Overload
    public String[] getAnswers(){
        return mAnswers;
    }

    public void setAnswers(String answers[]) {
        for (int i = 0; i < answers.length; i ++){
            mAnswers[i] = answers[i];
        }
    }

    public boolean isAlreadyDisplayed() {
        return mAlreadyDisplayed;
    }

    public void setAlreadyDisplayed(boolean mAlreadyDisplayed) {
        this.mAlreadyDisplayed = mAlreadyDisplayed;
    }
}
