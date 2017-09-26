package com.baibian.view;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by 任中豪 on 2017/9/22.
 */

    public abstract class PageView extends RelativeLayout {
        public PageView(Context context) {
            super(context);
        }
        public abstract void refresh();
    }
