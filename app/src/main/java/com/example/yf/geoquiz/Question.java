package com.example.yf.geoquiz;

/**
 * Created by YF on 2016/5/30.
 */
public class Question {

    private int mTextResID;
    private boolean mAnswerTrue;

    public Question(int textResId , boolean answerTrue){
        mTextResID = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResID() {
        return mTextResID;
    }

    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
