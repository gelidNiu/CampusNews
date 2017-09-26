package com.baibian.view.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.baibian.R;

/**
 * Created by Ellly on 2017/8/29.
 */

public class MessageItemItemView extends RelativeLayout {
    public MessageItemItemView(Context context) {
        super(context);
        init();
    }

    public MessageItemItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageItemItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.message_solid_item_item_layout, this);
    }
}
