package com.baibian.view.reusable_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;

/**
 * Created by Ellly on 2017/8/26.
 * 带一个可以展开收缩主体text view的button的view
 */

public class MyExpandableTextView extends RelativeLayout implements View.OnClickListener{

    private static final String TAG = "M_E_T_V";

    private TextView mExpandButton;
    private TextView mExpandedTextView;
    private TextView mCollapsedTextView;
    private String mContentText;
    private ExpandState mState;
//    private Handler
    /**
     * 先设置invisible测得高度后再设置为gone //gone时测得高度为0
     */
    private Runnable mRunnable = new Runnable() {
    @Override
    public void run() {
        mExpandedTextHeight = mExpandedTextView.getMeasuredHeight();
        mExpandedTextView.setVisibility(View.GONE);
    }
};

    private int mCollapsedTextHeight = 0;
    private int mExpandedTextHeight = 0;
    private boolean mFirstLoaded = true;

    public enum ExpandState{
        EXPANDED, COLLAPSED
    }
    public MyExpandableTextView(Context context) {
        super(context);
        init();
    }

    public MyExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTextContent(String text){
        changeText(text);
    }

    private void changeText(String text) {
        mContentText = text;
        onTextChanged();
    }

    private void onTextChanged() {
        mCollapsedTextView.setText(mContentText);
        mExpandedTextView.setText(mContentText);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_expandable_text_view, this);
        mState = ExpandState.COLLAPSED;
        mCollapsedTextView = (TextView) findViewById(R.id.collapsed_text_view);
        mExpandedTextView = (TextView) findViewById(R.id.expanded_text_view);
        mExpandButton = (TextView) findViewById(R.id.expand_btn);
        mExpandButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.expand_btn:
                switchWithState();
                break;
        }
    }

    private void switchWithState() {

        switch (mState){
            case COLLAPSED:
                mState = ExpandState.EXPANDED;
                mCollapsedTextView.setVisibility(View.GONE);
                mExpandedTextView.setVisibility(View.VISIBLE);
                Log.d(TAG + "_double_heights", mCollapsedTextHeight + " " + mExpandedTextHeight);
                ObjectAnimator mAnimator = ObjectAnimator.ofInt(mExpandedTextView, "height", mCollapsedTextHeight, mExpandedTextHeight);
                mAnimator.start();
                mExpandButton.setText(getCurrentText());
                break;
            case EXPANDED:
                mState = ExpandState.COLLAPSED;
                ObjectAnimator mCollapseAnimator = ObjectAnimator.ofInt(mExpandedTextView, "height", mExpandedTextHeight, mCollapsedTextHeight);
                mCollapseAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCollapsedTextView.setVisibility(View.VISIBLE);
                    }
                });
                mCollapseAnimator.start();
                mExpandButton.setText(getCurrentText());
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mFirstLoaded) {
            mCollapsedTextHeight = mCollapsedTextView.getMeasuredHeight();
            mExpandedTextView.post(mRunnable);
            mFirstLoaded = false;
        }
        Log.d(TAG + "_height", mExpandedTextView.getMeasuredHeight() + "  " + mExpandedTextView.getMaxHeight() + "  " + mExpandedTextView.getHeight());
    }

    private String getCurrentText() {
        String targetString = null;
        switch (mState){
            case COLLAPSED:
                targetString = getResources().getString(R.string.expand_text);
                break;
            case EXPANDED:
                targetString = getResources().getString(R.string.collaps_text);
                break;
        }
        return targetString;
    }
}
