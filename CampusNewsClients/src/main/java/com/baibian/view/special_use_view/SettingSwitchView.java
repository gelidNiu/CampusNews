package com.baibian.view.special_use_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.baibian.R;

/**
 * Created by Ellly on 2017/9/7.
 */

public class SettingSwitchView extends RelativeLayout {
    private Switch mSwitch;
    private TextView mDemonstrationText;
    public SettingSwitchView(Context context) {
        super(context);
        init(context);
    }

    public SettingSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttributes(context, attrs);
    }

    public SettingSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttributes(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.setting_switch_layout, this, true);
        mSwitch = (Switch) findViewById(R.id.setting_switch_switch);
        mDemonstrationText = (TextView) findViewById(R.id.setting_switch_text);
    }

    private void initAttributes(Context context, AttributeSet set) {
        TypedArray array = context.obtainStyledAttributes(set, R.styleable.SettingSwitchView);
        if (array != null){
            String declarationText = array.getString(R.styleable.SettingSwitchView_declaration_text);
//            float declarationTextSize = array.getDimension(R.styleable.SettingSwitchView_declaration_text_size, 0);
            mDemonstrationText.setText(declarationText);
//            mDemonstrationText.setTextSize(declarationTextSize);
            array.recycle();
        }
    }

    public Switch getSwitch() {
        return mSwitch;
    }
}
