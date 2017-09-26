package com.baibian.view.reusable_view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baibian.R;

/**
 * Created by 网上的
 * 模仿知乎的一个好玩的点击button
 */
public class RevealFollowButton extends FrameLayout {
    private OnFollowedClickListener mListener;
    protected boolean mIsFollowed;
    protected boolean mIsFirstInit = true;
    private TextView mFollowTv;
    private TextView mUnFollowTv;
    float mCenterX;
    float mCenterY;
    float mRevealRadius = 0;

    private Path mPath = new Path();

    public void setOnFollowedClickListener(OnFollowedClickListener listener){
        this.mListener = listener;
    }

    public RevealFollowButton(Context paramContext) {
        super(paramContext);
        init();
    }

    public RevealFollowButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public RevealFollowButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private boolean isValidClick(float x, float y) {
        if (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()) {
            return true;
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                mIsFirstInit =false;
                mCenterX = event.getX();
                mCenterY = event.getY();
                mRevealRadius = 0;
                mFollowTv.setVisibility(View.VISIBLE);
                mUnFollowTv.setVisibility(View.VISIBLE);
                setFollowed(!mIsFollowed, true);
                if (mListener != null){
                    mListener.onFollowedClick(mIsFollowed);
                }
                return true;
        }
        return false;
    }

    private void init() {
        initUnFollowTv();
        addView(this.mUnFollowTv);
        initFollowTv();
        addView(this.mFollowTv);

        mFollowTv.setPadding(30, 15, 30, 17);
        mUnFollowTv.setPadding(30, 15, 30, 17);
        setFollowed(false, false);
    }

    public void setFollowBackground(Drawable drawable){
        mFollowTv.setBackground(drawable);
    }
    public void setFollowTextColor(@ColorInt int colorInt){
        mFollowTv.setTextColor(colorInt);
    }
    public void setFollowTextContent(@StringRes int resId){
        mFollowTv.setText(resId);
    }
    public void setUnFollowTvTextContent(@StringRes int resId){
        mUnFollowTv.setText(resId);
    }
    public void setUnFollowTextColor(@ColorInt int colorInt){
        mUnFollowTv.setTextColor(colorInt);
    }
    public void setUnFollowBackground(Drawable drawable){
        mUnFollowTv.setBackground(drawable);
    }
    protected void initFollowTv() {
        mFollowTv = new TextView(getContext());
        mFollowTv.setText("取消");
        mFollowTv.setGravity(17);
        mFollowTv.setSingleLine();
        mFollowTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.corner_rectangle_followed));
        mFollowTv.setTextColor(Color.WHITE);
    }

    protected void initUnFollowTv() {
        mUnFollowTv = new TextView(getContext());
        mUnFollowTv.setText("关注");
        mUnFollowTv.setGravity(17);
        mUnFollowTv.setSingleLine();
        mUnFollowTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.corner_rectangle));
        mUnFollowTv.setTextColor(Color.BLACK);
    }

    public void setFollowed(boolean isFollowed, boolean needAnimate) {
        mIsFollowed = isFollowed;
        if (isFollowed) {
            mUnFollowTv.setVisibility(View.VISIBLE);
            mFollowTv.setVisibility(View.VISIBLE);
            mFollowTv.bringToFront();
        } else {
            mUnFollowTv.setVisibility(View.VISIBLE);
            mFollowTv.setVisibility(View.VISIBLE);
            mUnFollowTv.bringToFront();
        }
        if (needAnimate) {
            ValueAnimator animator = ObjectAnimator.ofFloat(mFollowTv, "empty", 0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(500L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    private boolean drawBackground(View paramView) {
        if(mIsFirstInit) {
            return true;
        }
        if (mIsFollowed && paramView == mUnFollowTv) {
            return true;
        } else if (!mIsFollowed && paramView == mFollowTv) {
            return true;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View paramView, long paramLong) {
        if (drawBackground(paramView)) {
            return super.drawChild(canvas, paramView, paramLong);
        }
        int i = canvas.save();
        mPath.reset();
        mPath.addCircle(mCenterX, mCenterY, mRevealRadius, Path.Direction.CW);
        canvas.clipPath(this.mPath);
        boolean bool2 = super.drawChild(canvas, paramView, paramLong);
        canvas.restoreToCount(i);
        return bool2;
    }

    public interface OnFollowedClickListener{
        void onFollowedClick(boolean isFollowed);
    }
}
