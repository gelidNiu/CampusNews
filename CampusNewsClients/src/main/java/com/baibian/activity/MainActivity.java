package com.baibian.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.activity.login.Login4Activity;
import com.baibian.activity.user_drawer.MessageAndNotificationActivity;
import com.baibian.activity.user_drawer.MyBookshelfActivity;
import com.baibian.activity.user_drawer.MyCollectionActivity;
import com.baibian.activity.user_drawer.RecyclerActivity;
import com.baibian.activity.user_drawer.user_setting.UserSettingActivity;
import com.baibian.activity.user_drawer.users_information.HisFocusActivity;
import com.baibian.adapter.Forums_Integration_Refresh_FootAdapter;
import com.baibian.adapter.NewsFragmentPagerAdapter;
import com.baibian.app.AppApplication;
import com.baibian.bean.ChannelItem;
import com.baibian.fragment.main.FindFragment;
import com.baibian.fragment.main.ForumsFragment;
import com.baibian.fragment.main.HomepageFragment;
import com.baibian.fragment.main.PeriodicalsFragment;
import com.baibian.listener.ReceiverTasksHelper;
import com.baibian.receiver.TasksReceiver;
import com.baibian.tool.BaseTools;
import com.baibian.view.ColumnHorizontalScrollView;
import com.baibian.view.SlidingDrawerView;
import com.baibian.view.reusable_view.RevealFollowButton;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ruffian.library.RTextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *  ģ�⻹ԭ����ͷ�� --�����Ķ���
 * author:XZY && RA
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    /**
     * �ĸ���Ƭ���������ı���
     */
    private HomepageFragment homepageFragment;//��һ����Ƭ
    private ForumsFragment forumsFragment;//�ڶ�����Ƭ
    private FindFragment findFragment;//��������Ƭ
    private PeriodicalsFragment periodicalsFragment;//���ĸ���Ƭ
    private View fragmentLayout1;
    private View fragmentLayout2;
    private View fragmentLayout3;
    private View fragmentLayout4;
    private ImageView fragmentImage1;
    private ImageView fragmentImage2;
    private ImageView fragmentImage3;
    private ImageView fragmentImage4;
    private FragmentManager fragmentManager;
    /**
     * �Զ���HorizontalScrollView
     */
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    LinearLayout mRadioGroup_content;
    LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    private ViewPager mViewPager;
    private ImageView button_more_columns;
    /**
     * �û�ѡ������ŷ����б�
     */
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    /**
     * ��ǰѡ�е���Ŀ
     */
    private int columnSelectIndex = 0;
    /**
     * ����Ӱ����
     */
    public ImageView shade_left;
    /**
     * ����Ӱ����
     */
    public ImageView shade_right;
    /**
     * ��Ļ���
     */
    private int mScreenWidth = 0;
    /**
     * Item���
     */
    private int mItemWidth = 0;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public SlidingMenu side_drawer;
    /**
     * head ͷ�� ���м��loading
     */
    private ProgressBar top_progress;
    /**
     * head ͷ�� �м��ˢ�°�ť
     */
    private ImageView top_refresh;
    /**
     * head ͷ�� �����˵� ��ť
     */
    private ImageView top_head;
    /**
     * head ͷ�� ���Ҳ�˵� ��ť
     */
    private ImageView top_more;
    /**
     * ͷ�����м�����
     */
    private TextView top_text;
    /**
     * ����CODE
     */
    public final static int CHANNELREQUEST = 1;
    /**
     * �������ص�RESULTCODE
     */
    public final static int CHANNELRESULT = 10;
//    private static final int STATE_REFRESHING = 3;

    private NewsFragmentPagerAdapter mAdapter;
    private int position1 = 0;

    /**
     * ʵ�ֵ�½�����UIΪԲ��ͷ��
     */
    private ImageView baibian_btn;//ͨ���ٱ��˺ŵ�¼
    private final int LOGIN4_REQUEST=11;//����login4activity��������
    private LinearLayout logout_layout_not_login;//δ��¼����
    private RelativeLayout login_layout;//����Բ��ͷ��Ĳ��֣���������ԭ���Ĳ���

    /**
     * ���������������
     */
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;//�ж��Ƿ��ǵ�һ�ε�½ʹ��
    private GestureDetector gestureDetector;
    private SwipeRefreshLayout integration_swiperefreshlayout;
    private Window mWindow;
    private RecyclerView mRecyclerView;
    private final static int FORUMS_INITIAL_PAGE = 0;

    //* 汪衣宇添加的
    private TasksReceiver mReceiver;
    private DrawerLayout mDrawerLayout;
    //    private NavigationView mNavigationView;
    private LinearLayout mNavigationView;
    private RelativeLayout mNavHeader;

    private CircleImageView mUserPortrait;
    private RevealFollowButton mPickButton;
    private TextView mLevel;
    private TextView mSignature;
    private TextView mSettingsButton;
    private RTextView mNightModeButton;
    private TextView mUserName;

    private ImageView mNaverHeaderBackImageView;

    private TextView mNaverMyFocus;
    private TextView mNaverMyCollection;
    private TextView mNaverMyBookshelf;
    private TextView mNaverMessageAndNotification;
    private TextView mNaverRecyclerStation;
    private TextView mNaverHistorySee;

    // *//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StrictMode.setThreadPolicy(new
//                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(
//                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        if (((AppApplication)getApplication()).isNightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.main);
        mWindow=getWindow();
        View integrationFragment=View.inflate(getApplicationContext(),R.layout.integration_fragment_layout,null);
        mRecyclerView=(RecyclerView)integrationFragment.findViewById(R.id.integration_recycler);
        integration_swiperefreshlayout=(SwipeRefreshLayout) integrationFragment.findViewById(R.id.integration_swiperefreshlayout);
        final Forums_Integration_Refresh_FootAdapter adapter=new Forums_Integration_Refresh_FootAdapter(getApplicationContext(),mWindow,mRecyclerView);
        gestureDetector=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onDoubleTap(MotionEvent event)//˫��ˢ�´�����
            {
                 List<String> newDatas = new ArrayList<String>();
                 for (int i = 0; i < 5; i++) {
                     /**
                      * ������ˢ�µĲ������ݵ�����
                      */
                     int index = i + 1;
                     newDatas.add("new �û�" + index);
                 }
                 adapter.addItem(newDatas);
                 integration_swiperefreshlayout.setRefreshing(false);
                 Toast.makeText(MainActivity.this, R.string.reflesh, Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(event);
            }
        });
        mScreenWidth = BaseTools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 4;// ???Item?????????1/4

//        initSlidingMenu();

//        init_guide();//��������ĳ�ʼ��

        //*汪衣宇添加的//
        initDrawerView();
        initImageRelatedReceiver();
        initPortraitInDrawer();
        // *//


        initViews();
        fragmentManager = getFragmentManager();
        // ��һ������ʱѡ�е�0��tab
        setTabSelection(0);




    }

    /**
     * �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
     */
    private void initViews() {
        fragmentLayout1 = findViewById(R.id.fragment1_layout);
        fragmentLayout2 = findViewById(R.id.fragment2_layout);
        fragmentLayout3 = findViewById(R.id.fragment3_layout);
        fragmentLayout4 = findViewById(R.id.fragment4_layout);
        fragmentImage1 = (ImageView) findViewById(R.id.fragment1_img);
        fragmentImage2 = (ImageView) findViewById(R.id.fragment2_img);
        fragmentImage3 = (ImageView) findViewById(R.id.fragment3_img);
        fragmentImage4 = (ImageView) findViewById(R.id.fragment4_img);
        fragmentLayout1.setOnClickListener(this);
        fragmentLayout2.setOnClickListener(this);
        fragmentLayout3.setOnClickListener(this);
        fragmentLayout4.setOnClickListener(this);
        top_text=(TextView) findViewById(R.id.top_text);

        top_head = (ImageView) findViewById(R.id.top_head);
        top_more = (ImageView) findViewById(R.id.top_more);
        top_refresh = (ImageView) findViewById(R.id.top_refresh);
       top_progress = (ProgressBar) findViewById(R.id.top_progress);
        top_head.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*if (side_drawer.isMenuShowing()) {
                    side_drawer.showContent();
                } else {
                    side_drawer.showMenu();
                }*/
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });
        top_more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * if (side_drawer.isSecondaryMenuShowing()) {
                 side_drawer.showContent();
                 } else {
                 side_drawer.showSecondaryMenu();
                 }
                 */
                // TODO Auto-generated method stub

            }
        });
        fragmentLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment1_layout:
                // ���������Ϣtabʱ��ѡ�е�1��tab
                InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fragmentLayout1.getWindowToken(), 0);//���д������������
                top_text.setText(R.string.LunXun);
                setTabSelection(0);
                break;
            case R.id.fragment2_layout:
                // ���������ϵ��tabʱ��ѡ�е�2��tab
                InputMethodManager imm2 = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(fragmentLayout1.getWindowToken(), 0);//���д������������
                setTabSelection(1);
                top_text.setText(R.string.LunGe);

                break;
            case R.id.fragment3_layout:
                // ������˶�̬tabʱ��ѡ�е�3��tab
                InputMethodManager imm3 = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(fragmentLayout1.getWindowToken(), 0);//���д������������
                setTabSelection(2);
                top_text.setText(R.string.LunBa);

                break;
            case R.id.fragment4_layout:
                // ������˶�̬tabʱ��ѡ�е�3��tab
                InputMethodManager imm4 = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                imm4.hideSoftInputFromWindow(fragmentLayout1.getWindowToken(), 0);//���д������������
                setTabSelection(3);
                break;
            case R.id.user_portrait:
                startActivity(new Intent(this, UsersImformationActivity.class));
                break;

            case R.id.user_setting:
                startActivity(new Intent(this, UserSettingActivity.class));
                break;
            case R.id.night_mode:
                int startTextColor = Color.parseColor("#000000");
                int endTextColor = Color.parseColor("#ffffff");
                int startBackgroundColor = Color.parseColor("#ffffff");
                int endBackgroundColor = Color.parseColor("#333366");
                int startBackHeaderColor = Color.parseColor("#ffa9a9a9");
                int endBackHeaderColor = Color.parseColor("#333399");
                AppApplication current = (AppApplication)getApplication();
                if (current.isNightMode()){
                    current.setNightMode(false);
                    ValueAnimator transitToNightAnimator = generateValueAnimator(endTextColor, startTextColor);
                    ValueAnimator transitToBackNightAnimator = generateBackAnimator(endBackgroundColor, startBackgroundColor);
                    ValueAnimator transitToBackHeaderAnimator = generateBackHeaderAnimator(endBackHeaderColor, startBackHeaderColor);
                    transitToBackHeaderAnimator.start();
                    transitToNightAnimator.start();
                    transitToBackNightAnimator.start();
                    mNightModeButton.setText("夜间模式");
                    mNightModeButton.setIconNormal(ContextCompat.getDrawable(this, R.drawable.ic_personal_night));

                }else {
//                    int startTextColor = AppApplication.getColorFromTheme(getTheme(), R.attr.colorValue);
                    current.setNightMode(true);
//                    int endTextColor = AppApplication.getColorFromTheme(getTheme(), R.attr.colorValue);
                    ValueAnimator transitToDayAnimator = generateValueAnimator(startTextColor, endTextColor);
                    ValueAnimator transitToBackDayAnimator = generateBackAnimator(startBackgroundColor, endBackgroundColor);
                    ValueAnimator transitToBackHeaderDayAnimator = generateBackHeaderAnimator(startBackHeaderColor, endBackHeaderColor);
                    transitToBackHeaderDayAnimator.start();
                    transitToBackDayAnimator.start();
                    transitToDayAnimator.start();
                    mNightModeButton.setText("日间模式");
                    mNightModeButton.setIconNormal(ContextCompat.getDrawable(this, R.drawable.ic_personal_day));

                }
                //会出现闪屏的情况，应该使用动画
//                recreate();
                break;
            case R.id.my_focus:
                startActivity(new Intent(this, HisFocusActivity.class));
                break;
            case R.id.my_bookshelf:
                startActivity(new Intent(this, MyBookshelfActivity.class));
                break;
            case R.id.recycle_station:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            case R.id.message_and_notification:
                startActivity(new Intent(this, MessageAndNotificationActivity.class));
                break;
            case R.id.my_collection:
                startActivity(new Intent(this, MyCollectionActivity.class));
                break;

            default:
                break;
        }
    }


    private ValueAnimator generateBackHeaderAnimator(int startColor, int endColor) {
        ValueAnimator transitAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor).setDuration(300);
        transitAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                mNavHeader.setBackgroundColor(color);
            }
        });
        return transitAnimation;
    }

    private ValueAnimator generateBackAnimator(int startTextColor, int endTextColor) {
        ValueAnimator transitAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startTextColor, endTextColor).setDuration(300);
        transitAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
//                mDrawerLayout.setBackgroundColor(color);
                mNavigationView.setBackgroundColor(color);

            }
        });
        return transitAnimation;
    }

    private ValueAnimator generateValueAnimator(int startTextColor, int endTextColor) {
        ValueAnimator transitAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startTextColor, endTextColor).setDuration(300);
        transitAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                mNightModeButton.setTextColor(color);
                mSettingsButton.setTextColor(color);
                mUserName.setTextColor(color);
                mLevel.setTextColor(color);
                mSignature.setTextColor(color);
                mNaverMessageAndNotification.setTextColor(color);
                mNaverMyCollection.setTextColor(color);
                mNaverRecyclerStation.setTextColor(color);
                mNaverMyFocus.setTextColor(color);
                mNaverHistorySee.setTextColor(color);
                mNaverMyBookshelf.setTextColor(color);
            }
        });
        return transitAnimation;
    }

    /**
     * ���ݴ����index����������ѡ�е�tabҳ��
     *
     */
    private void setTabSelection(int index) {
        // ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
        clearSelection();
        // ����һ��Fragment����
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
        hideFragments(transaction);
        switch (index) {
            case 0:
                fragmentImage1.setImageResource(R.mipmap.black_mipmap);
                fragmentLayout1.setBackgroundColor(getResources().getColor(R.color.main_layout_selected));
                if (homepageFragment == null) {
                    homepageFragment = new HomepageFragment();
                    transaction.add(R.id.content, homepageFragment);//����Ĵ�������û�б������ݣ�������һ���µ�ʵ�����ӡ�����
                } else {
                    transaction.show(homepageFragment);
                }
                break;
            case 1:

                fragmentImage2.setImageResource(R.mipmap.black_mipmap);
                fragmentLayout2.setBackgroundColor(getResources().getColor(R.color.main_layout_selected));
                if (forumsFragment == null) {
                    forumsFragment = new ForumsFragment();
                    transaction.add(R.id.content, forumsFragment);
                } else {
                    transaction.show(forumsFragment);
                }
                break;
            case 2:
                fragmentImage3.setImageResource(R.mipmap.black_mipmap);
                fragmentLayout3.setBackgroundColor(getResources().getColor(R.color.main_layout_selected));
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.content, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 3:
                fragmentImage4.setImageResource(R.drawable.periodicalsfragment_selected);
                fragmentLayout4.setBackgroundColor(getResources().getColor(R.color.main_layout_selected));
                if (periodicalsFragment == null) {
                    // ���NewsFragmentΪ�գ��򴴽�һ������ӵ�������
                    periodicalsFragment = new PeriodicalsFragment();
                    transaction.add(R.id.content, periodicalsFragment);
                } else {
                    // ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
                    transaction.show(periodicalsFragment);
                }
                break;
        }


        transaction.commit();
    }

    /**
     * ��������е�ѡ��״̬��
     */
    private void clearSelection() {
        fragmentImage1.setImageResource(R.mipmap.black_mipmap);
//        fragmentLayout1.setBackgroundColor(getResources().getColor(R.color.main_layout1_unselected));
     //   fragmentLayout1.setBackgroundColor(getResources().getColor(
      //          R.color.mainFragment_background
      //  ));
        fragmentImage2.setImageResource(R.mipmap.black_mipmap);
//        fragmentLayout2.setBackgroundColor(getResources().getColor(R.color.main_layout1_unselected));
        fragmentImage3.setImageResource(R.mipmap.black_mipmap);
//        fragmentLayout3.setBackgroundColor(getResources().getColor(R.color.main_layout1_unselected));
        fragmentImage4.setImageResource(R.drawable.periodicalsfragment);
        fragmentLayout4.setBackgroundColor(getResources().getColor(R.color.main_layout1_unselected));
    }

    /**
     * �����е�Fragment����Ϊ����״̬��
     *
     *            ���ڶ�Fragmentִ�в���������
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homepageFragment != null) {
            transaction.hide( homepageFragment);
        }
        if (forumsFragment != null) {
            transaction.hide(forumsFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (periodicalsFragment != null) {
            transaction.hide(periodicalsFragment);
        }
    }
//
//
//
//    /**
//     * ���������ʼ������
//     */
//    private void init_guide(){
//        preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
//        if (preferences.getBoolean("firststart", true)) {
//            editor = preferences.edit();
//            //����¼��־λ����Ϊfalse���´ε�¼ʱ������ʾ�״ε�¼����
//            editor.putBoolean("firststart", false);
//            editor.commit();
//            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//�Ժ�����
    protected void initSlidingMenu() {
        side_drawer = new SlidingDrawerView(this).initSlidingMenu();

        /**
         * ��¼�л���������
         */
       login_layout=(RelativeLayout) side_drawer.findViewById(R.id.login_layout);
        baibian_btn=(ImageView) side_drawer.findViewById(R.id.baibian_btn);//�ٱ��¼��ť
        logout_layout_not_login=(LinearLayout) side_drawer.findViewById(R.id.logout_layout_not_login);//δ��¼����

        baibian_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_baibian_btn=new Intent(MainActivity.this,Login4Activity.class);
                startActivityForResult(intent_baibian_btn,LOGIN4_REQUEST);
            }
        });
        login_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,UsersImformationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        /*if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (side_drawer.isMenuShowing() || side_drawer.isSecondaryMenuShowing()) {
                side_drawer.showContent();
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, R.string.Press_again_to_exit,
                            Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }*/
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                View navView = findViewById(R.id.nav_father_view);
                if (mDrawerLayout.isDrawerOpen(navView)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, R.string.Press_again_to_exit,
                            Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        //����MENU��ť�¼����������κβ���
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {

            case LOGIN4_REQUEST:
                if(resultCode==LOGIN4_REQUEST){
                    /**
                     * ��¼�л����ֲ���
                     */
                    logout_layout_not_login.setVisibility(View.GONE);//�ɲ�����ʧ
                    login_layout.setVisibility(View.VISIBLE);//�²��ֳ���
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //*
    private void initDrawerView() {
        mNightModeButton = (RTextView) findViewById(R.id.night_mode);
        mSettingsButton = (TextView) findViewById(R.id.user_setting);
        mNavigationView = (LinearLayout) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mNavHeader = (RelativeLayout) mNavigationView.getHeaderView(0);
        mNavHeader = (RelativeLayout) findViewById(R.id.nav_header);
        mUserPortrait = (CircleImageView) mNavHeader.findViewById(R.id.user_portrait);
        mPickButton = (RevealFollowButton) mNavHeader.findViewById(R.id.pick_button);
        mUserName = (TextView) mNavHeader.findViewById(R.id.user_name);
        mLevel = (TextView) mNavHeader.findViewById(R.id.user_level);
        mSignature = (TextView) mNavHeader.findViewById(R.id.signature_content);
//        mNaverHeaderBackImageView = (ImageView) mNavHeader.findViewById(R.id.header_back_color);

//        mNavigationView.setNavigationItemSelectedListener(this);

        mNaverMyFocus = (TextView) findViewById(R.id.my_focus);
        mNaverMyCollection = (TextView) findViewById(R.id.my_collection);
        mNaverMyBookshelf = (TextView) findViewById(R.id.my_bookshelf);
        mNaverRecyclerStation = (TextView) findViewById(R.id.recycle_station);
        mNaverMessageAndNotification = (TextView) findViewById(R.id.message_and_notification);
        mNaverHistorySee = (TextView) findViewById(R.id.history_see);

        mNaverHistorySee.setOnClickListener(this);
        mNaverMessageAndNotification.setOnClickListener(this);
        mNaverRecyclerStation.setOnClickListener(this);
        mNaverMyBookshelf.setOnClickListener(this);
        mNaverMyFocus.setOnClickListener(this);
        mNaverMyCollection.setOnClickListener(this);

        mSettingsButton.setOnClickListener(this);
        mNightModeButton.setOnClickListener(this);
        mUserPortrait.setOnClickListener(this);
    }


    private void initImageRelatedReceiver() {
        mReceiver = new TasksReceiver();
        mReceiver.setImageLoadingHelper(new ReceiverTasksHelper() {
            @Override
            public void doTasks() {
                Bitmap bitmap = EditPortraitActivity.getSaveImageShared(UsersImformationActivity.FILE_NAME_PORTRAIT);
                mUserPortrait.setImageBitmap(bitmap);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.baibian.image_change");
        registerReceiver(mReceiver, filter);
    }

    private void initPortraitInDrawer() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mUserPortrait.setImageBitmap(EditPortraitActivity.getSaveImageShared(UsersImformationActivity.FILE_NAME_PORTRAIT));
            }
        });
    }

/*
    @Override
    public void onBackPressed() {
        View navView = findViewById(R.id.nav_father_view);
        if (mDrawerLayout.isDrawerOpen(navView)) {
            mDrawerLayout.closeDrawer(navView);
        } else {
            super.onBackPressed();
        }
    }
*/




}