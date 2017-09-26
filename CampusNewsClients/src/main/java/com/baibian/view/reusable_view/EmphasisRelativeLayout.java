package com.baibian.view.reusable_view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baibian.tool.DataTools;

/**
 * Created by Ellly on 2017/8/26.
 * 点击的时候自带的下划线会变蓝
 */

public class EmphasisRelativeLayout extends RelativeLayout implements View.OnFocusChangeListener{

    private View mBottomLine;
    private View mBottomEmphasisLine;

    public EmphasisRelativeLayout(Context context) {
        super(context);
        init();
    }

    public EmphasisRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmphasisRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                requestFocus();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void init() {

        setFocusable(true);
        setFocusableInTouchMode(true);

        mBottomLine = new View(getContext());
        mBottomEmphasisLine = new View(getContext());
        mBottomLine.setBackgroundColor(Color.parseColor("#a197a1"));
        mBottomEmphasisLine.setBackgroundColor(Color.parseColor("#47bafe"));
        mBottomEmphasisLine.setVisibility(View.INVISIBLE);
        LayoutParams mParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DataTools.dip2px(getContext(), 1));
        mParams.addRule(ALIGN_PARENT_BOTTOM);
        addView(mBottomLine, mParams);
//        mParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DataTools.dip2px(getContext(), 2));
//        mParams.addRule(ALIGN_BOTTOM);
        addView(mBottomEmphasisLine, mParams);

//        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setOnFocusChangeListener(this);
//        requestFocus();
//        requestFocusFromTouch();
    }

/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                requestFocus();
                requestFocusFromTouch();
                break;
        }
        return false;
    }*/

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
//        ToastTools.ToastShow("ASDFGHJL");
        Log.d("THIS", "FocusChanged");
        if (hasFocus){
            mBottomEmphasisLine.setVisibility(View.VISIBLE);
        }else {
            mBottomEmphasisLine.setVisibility(View.INVISIBLE);
        }
    }
/*
    @Override
    public void onClick(View v) {
        requestFocus();
        requestFocusFromTouch();
    }*/
}
