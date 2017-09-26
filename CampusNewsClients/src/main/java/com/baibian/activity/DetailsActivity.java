package com.baibian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.adapter.Forums_Argument_Adapter;
import com.baibian.base.BaseActivity;
import com.baibian.information.ForumsArgument;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by XZY on 2017/4/13.
 * ?????????????/???
 * ?????????????????????Natural%20Right??????????????????????????????????????????????
 * ???��????????????????????????????????????????????????????????????????????????
 * ?????????????????????%20???????��??????????????????????????????????????????
 * ?????????????????????????????????????��??????????????��???????
 * ????????????????��????��????????????????????%20?????????��?????????????????????
 * ????????????????????????????????????????????????????????????????
 */

public class DetailsActivity extends BaseActivity implements View.OnClickListener, Forums_Argument_Adapter.ForumsOnItemClickListener {
    public View view3;//ϸ����ͼ
    private Context context;
    ViewPager pager = null;
    PagerTabStrip tabStrip = null;
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();
    public String TAG = "tag";
    Button backBt = null;
    private RelativeLayout forums_details_all_layout;
    private TagContainerLayout mTagContainerLayout1, mTagContainerLayout2, mTagContainerLayout3, mTagContainerLayout4;
    private Button addTag1;
    private EditText editTag;
    private ViewPager viewPager;
    List<Fragment> viewPagerList;
    private Activity activity;
    private Button begin_discussion;
    private RecyclerView argumentRecyclerView;//�۵�����recyclerviewd
    private List<ForumsArgument> forumsArgumentList = new ArrayList<>();//ÿ���۵�item������
    private Button publish_button;
    private TextView concentrate_tv;
    private ImageView praise_img;
    private boolean concentrateState = false;
    private boolean praiseState = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Forums_Argument_Adapter forums_argument_adapter;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private Window mWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forums_details);
        context = getApplicationContext();
        activity = this;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        initData();
        initArgument();
        initView();
        initViewPager();
        //begin_discussion.setOnClickListener(new View.OnClickListener()
        //{
        //    @Override
        //    public void onClick(View v)
        //    {
        //        Intent intent=new Intent(DetailsActivity.this,DiscussionActivity.class);
        //        startActivity(intent);
        //    }
        //});
//        viewpager????????
//        viewPagerList=new ArrayList<Fragment>();
//        viewPagerList.add(new overviewFragment());
//        viewPagerList.add(new subdivideFragment());
//        viewPager =(ViewPager) findViewById(R.id.pager);
//        fgViewPagerAdapter adapter=new fgViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);


//      ?????????
//        addTag1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editTag.setText(addTag1.getText());
//            }
//        });

    }

    /*
    **��ʼ���۵㣬���ǲ�������
     */
    private void initArgument() {
        ForumsArgument forumsArgument1 = new ForumsArgument();
        forumsArgument1.setArgument("���Ա��ƣ������ջ������", "��֮�񲢷�����ʹȻ,�������ڵ��ջ����Լ���ʧ����֪��֮�񲢷�����ʹȻ,�������ڵ��ջ����Լ���ʧ����֪��֮�񲢷�����ʹȻ,�������ڵ��ջ����Լ���ʧ����֪��֮�񲢷�����ʹȻ,�������ڵ��ջ����Լ���ʧ����֪", 100);
        forumsArgumentList.add(forumsArgument1);
    }

    private void initData() {
        List<String> list1 = new ArrayList<String>();
        list1.add("Java");
        list1.add("C++");
        list1.add("Python");
        list1.add("Swift");
        list1.add("???????????TAG?????????????TAG?????????????TAG?????????????TAG??");
        list1.add("PHP");
        list1.add("JavaScript");
        list1.add("Html");
        list1.add("Welcome to use AndroidTagView!");

        List<String> list2 = new ArrayList<String>();
        list2.add("China");
        list2.add("USA");
        list2.add("Austria");
        list2.add("Japan");
        list2.add("Sudan");
        list2.add("Spain");
        list2.add("UK");
        list2.add("Germany");
        list2.add("Niger");
        list2.add("Poland");
        list2.add("Norway");
        list2.add("Uruguay");
        list2.add("Brazil");

        String[] list3 = new String[]{"Persian", "?????", "?????", "Hello", "???", "????"};
        String[] list4 = new String[]{"Adele", "Whitney Houston"};

        mTagContainerLayout1 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout1);
        mTagContainerLayout2 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout2);
        mTagContainerLayout3 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout3);
        mTagContainerLayout4 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout4);

        // Set custom click listener
        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(DetailsActivity.this, "click-position:" + position + ", text:" + text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                AlertDialog dialog = new AlertDialog.Builder(DetailsActivity.this)
                        .setTitle("long click")
                        .setMessage("You will delete this tag!")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTagContainerLayout1.removeTag(position);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }

            @Override
            public void onTagCrossClick(int position) {
//                mTagContainerLayout1.removeTag(position);
                Toast.makeText(DetailsActivity.this, "Click TagView cross! position = " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Custom settings
//        mTagContainerLayout1.setTagMaxLength(4);

        // Set the custom theme
//        mTagContainerLayout1.setTheme(ColorFactory.PURE_CYAN);

        // If you want to use your colors for TagView, remember set the theme with ColorFactory.NONE
//        mTagContainerLayout1.setTheme(ColorFactory.NONE);
//        mTagContainerLayout1.setTagBackgroundColor(Color.TRANSPARENT);
//        mTagContainerLayout1.setTagTextDirection(View.TEXT_DIRECTION_RTL);

        // support typeface
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "iran_sans.ttf");
//        mTagContainerLayout.setTagTypeface(typeface);

        // adjust distance baseline and descent
//        mTagContainerLayout.setTagBdDistance(4.6f);

        // After you set your own attributes for TagView, then set tag(s) or add tag(s)
        mTagContainerLayout1.setTags(list1);
        mTagContainerLayout2.setTags(list2);
        mTagContainerLayout3.setTags(list3);
        mTagContainerLayout4.setTags(list4);

        final EditText text = (EditText) findViewById(R.id.text_tag);
        Button btnAddTag = (Button) findViewById(R.id.btn_add_tag);
        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagContainerLayout1.addTag(text.getText().toString());
                // Add tag in the specified position
//                mTagContainerLayout1.addTag(text.getText().toString(), 4);
            }
        });

//        mTagContainerLayout1.setMaxLines(1);


        // test in RecyclerView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setVisibility(View.VISIBLE);
//        TagRecyclerViewAdapter adapter = new TagRecyclerViewAdapter(this, list3);
//        adapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Click on TagContainerLayout", Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(adapter);
    }


    private void initViewPager() {
        viewPager.setAdapter(new PagerAdapter() {

            //viewpager?��????????
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            //?????��?????????????????
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }

            //??��????????????????
            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                if (position == 1) {
//                    TextView textViewbegin=(TextView) findViewById(R.id.begin_discussion);
//                    textViewbegin.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent=new Intent(DetailsActivity.this,DiscussionActivity.class);
//                            startActivity(intent);
//                        }
//                    });
                }
                return viewContainter.get(position);
            }

            /**
             �滻ԭ����view
             */
            private void updateViewPagerItem(ViewGroup container, View view, int index) {
                viewContainter.remove(index);
                view3 = LayoutInflater.from(viewPager.getContext()).inflate(R.layout.forums_argument_view, null);
                viewContainter.add(index, view3);
                viewPager.getAdapter().notifyDataSetChanged();
                // findViewById(getResources().getIdentifier("sysset_button"+(index+1), "id", "com.jzbyapp.suzhou")).requestFocus();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                SpannableStringBuilder ssb = new SpannableStringBuilder(" "
                        + titleContainer.get(position)); // space added before text for
                ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.black));//������ɫ
                ssb.setSpan(fcs, 1, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//???????????
                ssb.setSpan(new RelativeSizeSpan(1.2f), 1, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ssb;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.e(TAG, "--------changed:" + arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.e(TAG, "-------scrolled arg0:" + arg0);
                Log.e(TAG, "-------scrolled arg1:" + arg1);
                Log.e(TAG, "-------scrolled arg2:" + arg2);
            }

            @Override
            public void onPageSelected(int arg0) {
                Log.e(TAG, "------selected:" + arg0);
            }
        });
    }

    private void initView() {
        mWindow=getWindow();
        //begin_discussion=(Button)findViewById(R.id.begin_discussion) ;//������ⰴť
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) this.findViewById(R.id.tab_strip);
        backBt = (Button) this.findViewById(R.id.forums_details_back);
        forums_details_all_layout = (RelativeLayout) findViewById(R.id.forums_details_all_layout);
        List<String> tags = new ArrayList<String>();
        tags.add("123");
        tags.add("134455");
//        TagContainerLayout  mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
//        mTagContainerLayout.addTag(tags.get(0));
        //???tab??????????
        tabStrip.setDrawFullUnderline(false);
        //????tab??????

        tabStrip.setBackgroundColor(this.getResources().getColor(R.color.gray_white));
        //??????tab????????????
        tabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.gray_white));//�����ɫ

        tabStrip.setTextSpacing(200);
        tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);//��һ������Ϊָ����λ����

        View view1 = LayoutInflater.from(this).inflate(R.layout.forums_details_subdivide, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.forums_argument_view, null);//�������
        concentrate_tv = (TextView) view1.findViewById(R.id.concentrate_tv);
        praise_img = (ImageView) view1.findViewById(R.id.details_praise_img);
        concentrate_tv.setOnClickListener(this);
        praise_img.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) view2.findViewById(R.id.forums_argument_swiperefreshlayout);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        linearLayoutManager = new LinearLayoutManager(this);
        argumentRecyclerView = (RecyclerView) view2.findViewById(R.id.forums_argument_recycler);
        argumentRecyclerView.setLayoutManager(linearLayoutManager);
        argumentRecyclerView.setAdapter(forums_argument_adapter = new Forums_Argument_Adapter(this, forumsArgumentList,argumentRecyclerView,mWindow));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * ����ˢ���߼�
                 */
                new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         List<ForumsArgument> newForumsArgumentsList = new ArrayList<>();
                         for (int i = 0; i < 5; i++) {
                             ForumsArgument newArgument = new ForumsArgument();
                             newArgument.setArgument("��Ŀ", "������ֻ�������������籯���ȣ����б�ȴ�����ģ�ȴ���������ױ䡣��ɽ��������룬���������ղ��ϡ�", 0);
                             newForumsArgumentsList.add(newArgument);
                         }
                         forums_argument_adapter.addItem(newForumsArgumentsList);
                         swipeRefreshLayout.setRefreshing(false);
                         Toast.makeText(DetailsActivity.this, getString(R.string.refresh_data), Toast.LENGTH_SHORT).show();
                     }
                   }, 5000);
            }
        });
        argumentRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,int newState){
                super.onScrollStateChanged(recyclerView,newState);
                if(newState==RecyclerView.SCROLL_STATE_DRAGGING&&lastVisibleItem==forums_argument_adapter.getItemCount()-4){
                    /**
                     * ���ײ�item�ǵ���������itemʱ�����
                     */
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            List<ForumsArgument>newForumsArgumentsList=new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                ForumsArgument newArgument = new ForumsArgument();
                                newArgument.setArgument("��Ŀ", "������ֻ�������������籯���ȣ����б�ȴ�����ģ�ȴ���������ױ䡣��ɽ��������룬���������ղ��ϡ�", 0);
                                newForumsArgumentsList.add(newArgument);
                            }
                            forums_argument_adapter.addMoreItem(newForumsArgumentsList);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    System.out.println("success");
                }
                /**
                 * ����дһ��ˢ�����ݵķ�������
                 */
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==forums_argument_adapter.getItemCount()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<ForumsArgument>newForumsArgumentsList=new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                ForumsArgument newArgument = new ForumsArgument();
                                newArgument.setArgument("��Ŀ", "������ֻ�������������籯���ȣ����б�ȴ�����ģ�ȴ���������ױ䡣��ɽ��������룬���������ղ��ϡ�", 0);
                                newForumsArgumentsList.add(newArgument);
                            }
                            forums_argument_adapter.addMoreItem(newForumsArgumentsList);
                            swipeRefreshLayout.setRefreshing(false);
                            System.out.println("success2");
                        }
                    },3000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView,int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        forums_argument_adapter.setForumsOnItemClickListener(this);
        publish_button = (Button) view2.findViewById(R.id.publish_button);
        publish_button.setOnClickListener(this);

        //viewpager??????view
        viewContainter.add(view1);
        viewContainter.add(view2);
//        viewContainter.add(view3);
        titleContainer.add(getString(R.string.forums_details));
        titleContainer.add(getString(R.string.detaildivide));

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
//        mTagContainerLayout.setTags(tags);

        //????

    }


    public class TagRecyclerViewAdapter
            extends RecyclerView.Adapter<TagRecyclerViewAdapter.TagViewHolder> {

        private Context mContext;
        private String[] mData;
        private View.OnClickListener mOnClickListener;

        public TagRecyclerViewAdapter(Context context, String[] data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TagViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.view_recyclerview_item, parent, false), mOnClickListener);
        }

        @Override
        public void onBindViewHolder(TagViewHolder holder, int position) {
            holder.tagContainerLayout.setTags(mData);
            holder.button.setOnClickListener(mOnClickListener);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.mOnClickListener = listener;
        }

        class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TagContainerLayout tagContainerLayout;
            View.OnClickListener clickListener;
            Button button;

            public TagViewHolder(View v, View.OnClickListener listener) {
                super(v);
                this.clickListener = listener;
//                tagContainerLayout = (TagContainerLayout) v.findViewById(R.id.tagcontainerLayout);
                button = (Button) v.findViewById(R.id.button);
//                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        }
    }

    //????Fragment??ViewPager????????
    private class fgViewPagerAdapter extends FragmentPagerAdapter {

        public fgViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return viewPagerList.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewPagerList.size();
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_back_bt:
                finish();
                break;
            case R.id.concentrate_tv:
                if (!concentrateState) {
                    concentrate_tv.setText(R.string.have_concentrate);
                    concentrateState = true;
                } else {
                    concentrate_tv.setText(R.string.not_concentrate);
                    concentrateState = false;
                }
                break;
            case R.id.details_praise_img:
                if (!praiseState) {
                    praiseState = true;
                    praise_img.setImageResource(R.mipmap.have_praised_white);
                } else {
                    praiseState = false;
                    praise_img.setImageResource(R.mipmap.white_praise);
                }
                break;
            case R.id.publish_button:
                Intent intent = new Intent(DetailsActivity.this, PublishActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * item����ص�
     */
    @Override
    public void onItemClick() {
        /**
         * ��ת��һ��activity
         */
        Intent intent=new Intent(DetailsActivity.this,ArgumentActivity.class);
        startActivity(intent);
    }
}