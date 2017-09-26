package com.baibian.view.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ellly on 2017/8/26.
 */

public class MyBadgeView extends android.support.v7.widget.AppCompatTextView {

    private int mTargetWidth = 0;
    private int mTargetHeight = 0;

    private View mTargetView;
    private ViewGroup mFatherLayout;

    public MyBadgeView(Context context) {
        super(context);
        init();
    }

    public MyBadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyBadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTargetView(View targetView){
        mTargetView = targetView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension();
    }

    /*private int[] measureWidthAndHeight(int widthMeasureSpec, int heightMeasureSpec){
        int[] result = new int[2];
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY){
            result[0] = widthSpecSize;
        }else {
            result[0] = 100;
        }
    }*/
    private void init() {

    }
}
