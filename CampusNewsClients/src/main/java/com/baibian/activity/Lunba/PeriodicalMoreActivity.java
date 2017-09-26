package com.baibian.activity.Lunba;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.bean.PeriodicalBean;
import com.baibian.tool.superadapter.DataHolder;
import com.baibian.tool.superadapter.LayoutWrapper;
import com.baibian.tool.superadapter.SuperAdapter;
import com.baibian.tool.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;


public class PeriodicalMoreActivity extends AppCompatActivity {
    private SuperAdapter adapter;
    private  List<LayoutWrapper> data;
    DataHolder<PeriodicalBean> normalItemHolder;
    private RecyclerView mRecyclerView;
    private Button back;//返回按钮
    private TextView title;
    private List<PeriodicalBean> data_weekly,data_hero,data_subject,mdata;

    public PeriodicalMoreActivity() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_more);
        hideactionBar();//隐藏活动标题
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.more_recyclerView);
        back = (Button) findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Toast.makeText(PeriodicalMoreActivity.this, type+"", Toast.LENGTH_SHORT).show();
        //java中判断字符串是否相等：
        // （1）用“==”运算符，该运算符表示指向字符串的引用是否相同
        // （2）用equals方法，该方法比较的是字符串的内容是否相同
        //应该用equals方法比较字符串内容，比如此处，之前用“==”运算符，怎么判断判断都是“辩题锦集”
        if (type.equals("weekly")){
            title.setText("每周集锦");
            mdata=data_weekly;
        }else if(type.equals("hero")){
            title.setText("群英荟萃");
            mdata=data_hero;
        }else{
            title.setText("辩题集锦");
            mdata=data_subject;
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        data = new ArrayList<>();
        int[] layoutIds ={R.layout.activity_periodical_more_item};
        adapter = new SuperAdapter(PeriodicalMoreActivity.this, layoutIds);
        mRecyclerView.setAdapter(adapter);

        normalItemHolder=new DataHolder<PeriodicalBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, PeriodicalBean item, final int position) {
                final ImageView cover =  holder.getView(R.id.more_item_cover);
                final TextView title = holder.getView(R.id.more_item_title);
                final TextView brief = holder.getView(R.id.more_item_brief);
                final TextView readNumber = holder.getView(R.id.more_item_readNumber);
                cover.setImageResource(item.getCover());
                title.setText(item.getTitle());
                brief.setText(item.getBrief());
                readNumber.setText(String.valueOf(item.getReadNumber()));

                if(adapter.getmOnItemClickListener()!=null) {
                    holder.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.getmOnItemClickListener().OnItemClick(v, position);
                        }
                    });
                }
            }
        };

        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(0), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(1),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(2), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(3), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(4),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_more_item, 1,mdata.get(5), normalItemHolder));
        adapter.setData(data);

        adapter.setmOnItemClickLitener(new SuperAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(PeriodicalMoreActivity.this, "item"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PeriodicalMoreActivity.this,PeriodicalDetailActivity.class);
                startActivity(intent);
            }
        });


//        mRecyclerView.setAdapter(adapter);
//        adapter.setOnItemClickLitener_more(new PeriodicalMoreAdapter.OnItemClickListener_more() {

//            @Override
//            public void OnItemClick_more(View view,int position) {
//                    Toast.makeText(PeriodicalMoreActivity.this, position+"", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(PeriodicalMoreActivity.this,PeriodicalDetailActivity.class);
//                    startActivity(intent);
//
//            }


//        });

        //返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void hideactionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    private void initData() {

        data_weekly = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover1);
            data.setBrief("如果你无法简洁地表达你的想法，那只能说明你还不够了解它.\n" +
                    "                    ——阿尔伯特·爱因斯坦");
            data.setReadNumber(7);
            data.setTitle("第999期：逆境顺境哪个更容易成才");
            data_weekly.add(data);
        }
        data_hero = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover2);
            data.setBrief("...");
            data.setReadNumber(7);
            data.setTitle("第999期");
            data_hero.add(data);
        }
        data_subject = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover3);
            data.setBrief("...");
            data.setReadNumber(7);
            data.setTitle("第999期");
            data_subject.add(data);
        }

    }
}
