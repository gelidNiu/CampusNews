package com.baibian.activity.user_drawer;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baibian.R;
import com.baibian.app.AppApplication;
import com.baibian.bean.MyCollectionItemContent;
import com.baibian.tool.RecyclerViewCommonTool.CommonAdapter;
import com.baibian.tool.RecyclerViewCommonTool.ViewHolder;
import com.baibian.view.reusable_view.MyExpandableTextView;

import java.util.List;

public class MyCollectionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<MyCollectionItemContent.MyCollectionItem> mData;
    private Toolbar mToolbar;
    private RelativeLayout mRootView;
    private RelativeLayout.LayoutParams mLayoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((AppApplication)getApplication()).isNightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_my_collection);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRootView = (RelativeLayout) findViewById(R.id.my_collection_root_view);
        mData = MyCollectionItemContent.mData;

        mRecyclerView = new RecyclerView(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_item_decoration_drawable));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommonAdapter<MyCollectionItemContent.MyCollectionItem>(this, R.layout.my_collection_item_layout, mData) {

            MyExpandableTextView mTextView;
            ImageView mCloseBtn;
            @Override
            public void convert(final ViewHolder holder, MyCollectionItemContent.MyCollectionItem myCollectionItem) {
                mTextView = (MyExpandableTextView) holder.getItemView().findViewById(R.id.my_collection_comment_text);
                mCloseBtn = (ImageView) holder.getItemView().findViewById(R.id.my_collection_close_btn);

                mCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mData.remove(position);
                        notifyItemRemoved(position);
                        sendMessageToBack();
                    }
                });
                mTextView.setTextContent("这就是李云龙的力量，哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
            }
        });
        mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.addRule(RelativeLayout.BELOW, R.id.app_bar);
        mRootView.addView(mRecyclerView, mLayoutParams);
    }

    private void sendMessageToBack() {
        //TODO
    }
}
