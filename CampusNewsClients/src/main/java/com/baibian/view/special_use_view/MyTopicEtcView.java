package com.baibian.view.special_use_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.baibian.R;

/**
 * Created by Ellly on 2017/9/12.
 */

public class MyTopicEtcView extends RelativeLayout {
    public MyTopicEtcView(Context context) {
        super(context);
        init();
    }

    public MyTopicEtcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTopicEtcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.my_topic_etc_common_layout, this, true);
    }

}
