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

import com.baibian.R;
import com.baibian.tool.RecyclerViewCommonTool.CommonAdapter;
import com.baibian.tool.RecyclerViewCommonTool.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RankingListActivity extends AppCompatActivity {

    RecyclerView ranking_list;
    Toolbar mToolbar;

    /**
     * 大神数据结构
     */
    static class God{
        private int mHeader;
        private String mName;
        private String mField;
        private String mAchievement;
        private long mLikeNumber;

    }
    private List<God> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        initToolbar();


        ranking_list=(RecyclerView)findViewById(R.id.rv_ranking_list);
        ranking_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_bg));
        ranking_list.addItemDecoration(mDecoration);

        mData.add(new God());
        ranking_list.setAdapter(new CommonAdapter<God>(this, R.layout.activity_ranking_list_item, mData){
            @Override
            public void convert(ViewHolder holder, RankingListActivity.God categoryChapter) {
//
//                TextView chapterTitle = (TextView)  holder.getItemView().findViewById(R.id.category_name);
//                TextView chapterPurchaseState = (TextView) holder.getItemView().findViewById(R.id.category_purchase_state);

            }
        });
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
