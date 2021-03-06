package com.example.yf.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    //用来保存当前mCurrentIndex的值，
    //修正因为设备旋转造成的Activity销毁带来的题目回到初始
    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;

    private Button mCheatButton;

    private Button mNextButton;
    private Button mPrevButton;

    private TextView mQuestionTextView;

    //题库
    private Question [] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans , true),
            new Question(R.string.question_mideast , true),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas , true),
            new Question(R.string.question_aisa , true)
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Oncreat(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到cheatActivity
//                Intent i = new Intent(QuizActivity.this , CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this , answerIsTrue);
//                startActivity(i);
                startActivityForResult(i , REQUEST_CODE_CHEAT);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuiz();
                mIsCheater = false;
                updateQuestion();
            }
        });

        updateQuestion();

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0){
                    Toast.makeText(QuizActivity.this , "这是第一题！" , Toast.LENGTH_LONG).show();
                }
                else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        //检查存储的bundle信息
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX , 0);
        }
//        updateQuestion();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wanAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG , "saveInstanceState");
        saveInstanceState.putInt(KEY_INDEX , mCurrentIndex);

    }

    //自定义监听事件。实现点击文字（题目）跳转到下一题。
    public  void nextQ(View view){
        nextQuiz();
    }

    //对公共方法进行抽取，以方便共用。
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    //抽取。关于下一题的点击事件（包括点击按钮和点击文字）
    private void nextQuiz(){
        if (mCurrentIndex == mQuestionBank.length - 1){
            Toast.makeText(QuizActivity.this , "这是最后一题！" , Toast.LENGTH_LONG).show();
        }
        else{
            //debug测试
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        }
    }

    //增加答题TURE或者FALSE的逻辑判断。
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }
        else{
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this , messageResId , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG , "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG , "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG , "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG , "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG , "onDestroy() called");
    }

}
