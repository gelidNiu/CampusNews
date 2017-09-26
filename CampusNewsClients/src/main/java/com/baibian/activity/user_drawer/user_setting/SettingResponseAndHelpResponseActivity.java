package com.baibian.activity.user_drawer.user_setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baibian.R;
import com.baibian.app.AppApplication;
import com.baibian.tool.ToastTools;

public class SettingResponseAndHelpResponseActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditText;
    private Button mSubmitButton;
    private String mContent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((AppApplication)getApplication()).isNightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_setting_response_and_help_response);

        mToolbar = (Toolbar) findViewById(R.id.setting_r_h_r_tool_bar);
        mEditText = (EditText) findViewById(R.id.setting_r_h_r_content_edit_text);
        mSubmitButton = (Button) findViewById(R.id.setting_r_h_r_content_bottom_btn);

        mSubmitButton.setOnClickListener(this);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_r_h_r_content_bottom_btn:
                mContent = mEditText.getText().toString();
                if (mContent.isEmpty()){
                    ToastTools.ToastShow("请输入反馈内容");
                }else {
                    submitContent();
                    finish();
                }
                break;
        }
    }

    private void submitContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 这里上传数据
            }
        }).start();
        ToastTools.ToastShow(mContent);
    }
}
