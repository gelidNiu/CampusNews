package com.baibian.activity;

/**
 * Created by 任中豪 on 2017/9/22.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.adapter.CommentAdapter;
import com.baibian.information.Comments;
import com.baibian.view.PageView;
import com.baibian.viewpager.PageOne;
import com.baibian.viewpager.PageThree;
import com.baibian.viewpager.PageTwo;
import java.util.ArrayList;
import java.util.List;




public class ArgumentActivity extends AppCompatActivity implements CommentAdapter.OnItemClickListener {

    private List<Comments> commentsList = new ArrayList<>();

    private ViewPager mViewPager;

    //    private TabLayout mTabLayout;

    private Toolbar mToolbar;

    private List<PageView> pageList;

    private final static String TITLE = "以成败论英雄是否可取";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.argumentactivity_layout);
       initData();
        initView();
       initComments();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    private void initData() {
        // TODO: 2017/9/22      ViewPager添加内容
        pageList = new ArrayList<>();
        pageList.add(new PageOne(ArgumentActivity.this));
        pageList.add(new PageTwo(ArgumentActivity.this));
        pageList.add(new PageThree(ArgumentActivity.this));
    }


    private void initView() {
        // TODO: 2017/9/22 TOOLBAR&ViewPager初始化
        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TITLE);
        //mToolbar.setNavigationIcon(R.drawable.order);
       // mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View view) {
           //     Toast.makeText(ArgumentActivity.this, "返回", Toast.LENGTH_SHORT).show();
           // }
        //});
        Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.sift:
                        msg += "Click order";
                        break;
                    case R.id.collect:
                        msg += "Click collect";
                        break;
                    case R.id.history:
                        msg += "Click history";
                        break;
                    case R.id.report:
                        msg += "Click report";
                        break;

                }

                if(!msg.equals("")) {
                    Toast.makeText(ArgumentActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
       mViewPager.setAdapter(new SamplePagerAdapter());
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter(commentsList,ArgumentActivity.this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
//        mTabLayout.addTab(mTabLayout.newTab().setText("Page one"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Page two"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Page three"));
//        initListener();
    }

    private void initComments() {
        for (int i = 0; i < 10; i++) {
            Comments example = new Comments("2017-7-15", true, "花生酱", "此价值观是否可能是错的价值观？", "以结果作为判定标准可能会掩盖所有的弱点与不足？", R.drawable.head_image);
            commentsList.add(example);
        }
    }
//    private void initListener() {
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//
//    }

    private class SamplePagerAdapter extends PagerAdapter {
        /**viewpager 的 adapter**/

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.sift:
                Intent intent=new Intent(ArgumentActivity.this,ResponseActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(){
        Intent intent=new Intent(ArgumentActivity.this,ScanArgumentActivity.class);
        startActivity(intent);
    }
}

