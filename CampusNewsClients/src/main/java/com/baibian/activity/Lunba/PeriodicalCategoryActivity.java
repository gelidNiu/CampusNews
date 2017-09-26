package com.baibian.activity.Lunba;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.tool.RecyclerViewCommonTool.CommonAdapter;
import com.baibian.tool.RecyclerViewCommonTool.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalCategoryActivity extends AppCompatActivity {
    /**参数*/
    private int mFreeChapters = 1;//免费章节数
    private int mTotalChapters =10;//总章节数
    private int mPurchaseChapters = 2;//我已购买的章节数
    /**状态*/
    private static final int FREE=1;
    private static final int PURCHASED=2;
    private static final int UNPAID=3;

    /**
     * 章节数据结构
     */
    static class CategoryChapter{
        private String mChapterTitle;
        private int mState;

        public int getmState() {
            return mState;
        }

        public void setmState(int mState) {
            this.mState = mState;
        }

        public String getmChapterTitle() {
            return mChapterTitle;
        }

        public void setmChapterTitle(String mChapterTitle) {
            this.mChapterTitle = mChapterTitle;
        }
    }



    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private List<CategoryChapter> mData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_category);
        initVariousViews();
        initRecyclerRelations();
        initToolbar();
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

    private void initVariousViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    private void initRecyclerRelations() {
        /**
         * 模拟数据源
         */
        for (int i = 0; i < mTotalChapters-1; i++){
            CategoryChapter data =new CategoryChapter();
            //注意i从0开始
            if(i<mFreeChapters){
                data.setmState(FREE);
            }else if(i>mFreeChapters+mPurchaseChapters-1){
                data.setmState(UNPAID);
            }else{
                data.setmState(PURCHASED);
            }

            mData.add(data);



        }



        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_bg));
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.setAdapter(new CommonAdapter<CategoryChapter>(this, R.layout.activity_periodical_category_item, mData){
            @Override
            public void convert(ViewHolder holder, CategoryChapter categoryChapter) {
                    int state = categoryChapter.getmState();
                    TextView chapterTitle = (TextView)  holder.getItemView().findViewById(R.id.category_name);
                    TextView chapterPurchaseState = holder.getView(R.id.category_purchase_state);
                    TextView chapter=holder.getView(R.id.category_purchase_state);

                    if (state==FREE){
                        chapterPurchaseState.setText("免费");
                    }else if(state==PURCHASED){
                        chapterPurchaseState.setText("已购");
                        chapterPurchaseState.setTextColor(getResources().getColor(R.color.green));
                    }else{
                        chapterPurchaseState.setText("付费");
                        chapterPurchaseState.setTextColor(getResources().getColor(R.color.light_red));
                    }

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
