package com.baibian.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.view.PageView;

/**
 * Created by 任中豪 on 2017/9/22.
 */

public class PageOne extends PageView {
    public PageOne(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        TextView textContent = (TextView) view.findViewById(R.id.tab_content);
        textView.setText("“以成败论英雄是一种价值观。”");
        textContent.setText("成败英雄论不需要考虑对错，它作为一种价值观，就是可取的。成败英雄论不需要考虑对错，它作为一种价值观，就是可取的");
        addView(view);

    }

    @Override
    public void refresh() {

    }
}
