package com.baibian.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baibian.R;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText summary_edt;
    private EditText details_edt;
    private TextView summary_num_tv;
    private TextView details_num_tv;
    private Button back_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        hideActionBar();
        initView();
    }
public void hideActionBar()
{
    ActionBar actionBar=getSupportActionBar();
    if(actionBar!=null)
    {
        actionBar.hide();
    }
}

    public void initView() {
        summary_edt = (EditText) findViewById(R.id.publish_summary_edt);
        details_edt = (EditText) findViewById(R.id.publish_details_edt);
        summary_num_tv=(TextView)findViewById(R.id.publish_summary_wordNum_tv);
        details_num_tv=(TextView)findViewById(R.id.explain_wordNum_tv);
        summary_edt.addTextChangedListener(new WordNumLimitTextWatcher(summary_edt,50,summary_num_tv));
        details_edt.addTextChangedListener(new WordNumLimitTextWatcher(details_edt,100,details_num_tv));
        back_bt=(Button)findViewById(R.id.publish_back_bt);
        back_bt.setOnClickListener(this);
    }

    /**
     * 有限制字数功能的TextWatcher
     */
    private class WordNumLimitTextWatcher implements TextWatcher {
        private EditText mEditText;
        private int limitNum;
        private int selectionStart;
        private int selectionEnd;
        private int currentWordNum;
        private TextView wordNumTv;

        private WordNumLimitTextWatcher(EditText mEditText, int limitNum,TextView wordNumTv) {
            this.mEditText = mEditText;
            this.limitNum = limitNum;
            this.wordNumTv=wordNumTv;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            currentWordNum = s.length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            wordNumTv.setText(currentWordNum + "/" + limitNum);
            selectionStart=mEditText.getSelectionStart();
            selectionEnd=mEditText.getSelectionEnd();
            if(currentWordNum>limitNum)
            {
                s.delete(selectionStart-1,selectionEnd);
                mEditText.setText(s);
                selectionEnd=mEditText.getSelectionEnd();
                mEditText.setSelection(selectionEnd);
            }
        }

    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.publish_back_bt:
                finish();
                break;
        }
    }

}
