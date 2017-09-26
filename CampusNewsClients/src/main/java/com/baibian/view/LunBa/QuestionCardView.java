package com.baibian.view.LunBa;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;


/**
 * Created by Liao on 2017/8/26.
 */

public class QuestionCardView extends RelativeLayout {


    //QuestionContent数据结构
    public static class QuestionContent{
        private String mQuestionTitle;
        private String mQuestionBrief;
        private int mQuestionExpection;

        public QuestionContent(String mQuestionTitle, String mQuestionBrief, int mQuestionExpection) {
            this.mQuestionTitle=mQuestionTitle;
            this.mQuestionBrief=mQuestionBrief;
            this.mQuestionExpection=mQuestionExpection;
        }

        public int getmQuestionExpection() {
            return mQuestionExpection;
        }
        public void setmQuestionExpection(int mQuestionExpection) {
            this.mQuestionExpection = mQuestionExpection;
        }

        public String getmQuestionBrief() {
            return mQuestionBrief;
        }
        public void setmQuestionBrief(String mQuestionBrief) {
            this.mQuestionBrief = mQuestionBrief;
        }

        public String getmQuestionTitle() {
            return mQuestionTitle;
        }
        public void setmQuestionTitle(String mQuestionTitle) {
            this.mQuestionTitle = mQuestionTitle;
        }
    }


    private QuestionCardView.QuestionContent mQuestionContent;
    private CardView mCardRoot;
    private TextView mTitle;
    private TextView mExpectation;
    private TextView mBrief;
    private TextView mJoin;


    public void setContent(QuestionCardView.QuestionContent content){
        mQuestionContent = content;
    }

    public QuestionCardView setTitleText(String text){
        mTitle.setText(text);
        return this;
    }

    public QuestionCardView setBriefText(String text){
        mBrief.setText(text);
        return this;
    }

    public QuestionCardView(Context context) {
        super(context);
        init();
    }

    public QuestionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.activity_find_questions_item, this);
        mCardRoot = (CardView) findViewById(R.id.question_root_view);
        mCardRoot.setCardElevation(30);
        mBrief = (TextView) findViewById(R.id.question_content);
        mTitle = (TextView) findViewById(R.id.question_title);
        mExpectation = (TextView) findViewById(R.id.question_expectation);
        mJoin = (TextView) findViewById(R.id.question_join);
    }

}
