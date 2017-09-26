package com.baibian.activity.Lunba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baibian.R;
import com.baibian.tool.TPrompt;
import com.baibian.view.LunBa.QuestionCardView;




public class FindQuestionsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    LinearLayout mQuestionFatherLayout;
    ImageView add_questions;
    String add_title;
    String add_brief;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_questions);
        mQuestionFatherLayout = (LinearLayout) findViewById(R.id.question_father_view_layout);
        mQuestionFatherLayout.addView(new QuestionCardView(this));
        add_questions=(ImageView)findViewById(R.id.iv_add_questions);
        add_questions.setOnClickListener(this);
        //

        initToolbar();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_questions:
                Intent intent1 = new Intent(FindQuestionsActivity.this, InitiateDebateActivity.class);
                startActivityForResult(intent1,1);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                      TPrompt toast = new TPrompt(FindQuestionsActivity.this);
                      toast.showToast("发表淘题成功！");
                    add_title = data.getStringExtra("question_title");
                    add_brief= data.getStringExtra("question_brief");
                    QuestionCardView cardView = new QuestionCardView(FindQuestionsActivity.this);
                    cardView.setBriefText(add_brief);
                    cardView.setTitleText(add_title);
                    mQuestionFatherLayout.addView(cardView, 0);//在顶端添加评论

                }
                break;
            default:
        }
    }

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
