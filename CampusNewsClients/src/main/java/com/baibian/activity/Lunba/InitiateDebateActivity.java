package com.baibian.activity.Lunba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.view.LunBa.Checkbox.CheckBox_diy;
import com.baibian.tool.ToastTools;

import java.util.ArrayList;

public class InitiateDebateActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ImageView complete;
    EditText et_debate_title;
    EditText et_debate_brief;
    EditText et_debate_background;
    TextView tv_debate_title_left;
    TextView tv_debate_brief_left;
    Boolean item_flag=false;
    Boolean title_flag=false;
    Boolean brief_flag=false;
    Boolean background_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_debate);
        complete=(ImageView)findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ArrayList<String> list = new ArrayList<String>();
                final CheckBox_diy cb_1=(CheckBox_diy) findViewById(R.id.cb_1);
                final CheckBox_diy cb_2=(CheckBox_diy) findViewById(R.id.cb_2);
                final CheckBox_diy cb_3=(CheckBox_diy) findViewById(R.id.cb_3);
                final CheckBox_diy cb_4=(CheckBox_diy) findViewById(R.id.cb_4);
                final CheckBox_diy cb_5=(CheckBox_diy) findViewById(R.id.cb_5);
                final CheckBox_diy cb_6=(CheckBox_diy) findViewById(R.id.cb_6);
                final CheckBox_diy cb_7=(CheckBox_diy) findViewById(R.id.cb_7);
                final CheckBox_diy cb_8=(CheckBox_diy) findViewById(R.id.cb_8);
                final EditText editText=(EditText)findViewById(R.id._et_add_item);
                if(cb_1.isChecked()){list.add(cb_1.getText());}
                if(cb_2.isChecked()){
                    list.add(cb_2.getText());}
                if(cb_3.isChecked())list.add(cb_3.getText());
                if(cb_4.isChecked())list.add(cb_4.getText());
                if(cb_5.isChecked())list.add(cb_5.getText());
                if(cb_6.isChecked())list.add(cb_6.getText());
                if(cb_7.isChecked())list.add(cb_7.getText());
                if(cb_8.isChecked())list.add(cb_8.getText());
                if(editText.getText().toString().trim().length() != 0)list.add(editText.getText().toString());
                if(list.size()==0)item_flag=false;
                else item_flag=true;
                if(et_debate_background.getText().toString().trim().length()==0)
                    background_flag=false;
                else background_flag=true;

                if (!item_flag){
                    ToastTools.ToastShow("您还没有选择标签！");}
                if (!title_flag){ToastTools.ToastShow("您还没有填写辩题！");}
                if (!brief_flag){ToastTools.ToastShow("您还没有填写简介！");}
                if (!background_flag){ToastTools.ToastShow("您还没有填写背景！");}


                if(item_flag&&title_flag&&brief_flag&&background_flag){
                    String toast="item:";
                    for(int i=0;i<list.size();i++)
                    {
                        toast=toast+" "+(list.get(i));
                    }
                    ToastTools.ToastShow(toast);
                    Intent intent=new Intent();
                    intent.putExtra("question_title",et_debate_title.getText().toString());
                    intent.putExtra("question_brief",et_debate_brief.getText().toString());
                    intent.putExtra("question_background",et_debate_background.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();



                }

            }

        });
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        initToolbar();
        et_debate_title=(EditText)findViewById(R.id.debate_title);
        et_debate_brief=(EditText)findViewById(R.id.debate_brief);
        et_debate_background=(EditText)findViewById(R.id.debate_background);
        tv_debate_title_left=(TextView)findViewById(R.id.debate_title_left);
        tv_debate_brief_left=(TextView)findViewById(R.id.debate_brief_left);

        et_debate_title.addTextChangedListener(mTextWatcher_title);
        et_debate_brief.addTextChangedListener(mTextWatcher_brief);
    }


    TextWatcher mTextWatcher_title = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = et_debate_title.getSelectionStart();
            editEnd =et_debate_title.getSelectionEnd();
            int textLength=temp.toString().trim().length();
            if (textLength!=0)title_flag=true;
            else title_flag=false;

            tv_debate_title_left.setText("剩余字数：" + (40-textLength));
            if (textLength> 40) {
                ToastTools.ToastShow("超出字数限制！");
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                et_debate_title.setText(s);
                et_debate_title.setSelection(tempSelection);
            }
        }
    };//辩题字数监听
    TextWatcher mTextWatcher_brief = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }
        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = et_debate_brief.getSelectionStart();
            editEnd =et_debate_brief.getSelectionEnd();
            int textLength=temp.toString().trim().length();
            if (textLength!=0)brief_flag=true;
            else brief_flag=false;
            tv_debate_brief_left.setText("剩余字数：" + (100-textLength));
            if (textLength> 100) {
                ToastTools.ToastShow("超出字数限制！");
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                et_debate_brief.setText(s);
                et_debate_brief.setSelection(tempSelection);
            }
        }
    };//简介字数监听


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return true;
    }

}
