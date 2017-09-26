package com.baibian.activity.user_drawer.user_setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.baibian.R;
import com.baibian.base.MyBaseActivity;

public class SettingResponseAndHelpActivity extends MyBaseActivity implements View.OnClickListener{

    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_response_and_help);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.setting_r_h_response);
        mRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_r_h_response:
                startActivity(new Intent(this, SettingResponseAndHelpResponseActivity.class));
                break;
        }
    }
}
