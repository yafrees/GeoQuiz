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

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public int getmTextResID() {
        return mTextResID;
    }

    public void setmTextResID(int mTextResID) {
        this.mTextResID = mTextResID;
    }
}
