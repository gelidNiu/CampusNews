package com.baibian.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.baibian.tool.DataTools;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ellly on 2017/7/31.
 */

public class PortraitBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private float mStartY = 0;
    private int mStartHeight = 0;
    private float percent = 0;
    /*
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, CircleImageView child, View target, int dx, int dy, int[] consumed) {
        child.getLayoutParams().height -= dy;
        Log.d("behavior_test", "BehaviorTest" + dx + "     " + dy);
    }*/


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {

        Context mContext = child.getContext();
        int gap = DataTools.dip2px(mContext, 10);
        if (mStartY == 0 && mStartHeight == 0){
            mStartY = dependency.getY() + gap;
            mStartHeight = child.getLayoutParams().height;
        }
        percent = (dependency.getY() + gap)/ mStartY;
        if (percent >= 0) {
            child.setScaleX(percent);
            child.setScaleY(percent);
        }
        Log.d("child_bug_transition", child.getX() + "  " + child.getY());
        Log.d("dependent_view", child.getLayoutParams().height + "   " + dependency.getHeight());
        return false;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        Log.d("is_app_bar_layout", (dependency instanceof NestedScrollView) + "" );
        return dependency instanceof NestedScrollView;
    }

    public PortraitBehavior(Context context, AttributeSet attr){
        super(context, attr);
    }
}
