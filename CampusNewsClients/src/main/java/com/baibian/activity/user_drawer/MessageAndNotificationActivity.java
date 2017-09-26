package com.baibian.activity.user_drawer;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.baibian.R;
import com.baibian.app.AppApplication;
import com.baibian.base.MyBaseActivity;
import com.baibian.bean.MessageAndNotificationMessage.MessageChatItemContent;
import com.baibian.bean.MessageAndNotificationMessage.MessageDetailItem;
import com.baibian.bean.MessageAndNotificationMessage.MessageFriendRightItem;
import com.baibian.bean.MessageAndNotificationMessage.MessageGeneralItem;
import com.baibian.bean.MessageAndNotificationMessage.MessageSolidItem;
import com.baibian.bean.MessageAndNotificationMessage.MultipleTypes;
import com.baibian.fragment.messy_fiction.ChatFragment;
import com.baibian.fragment.messy_fiction.FriendsFragment;
import com.baibian.fragment.messy_fiction.GroupChatFragment;
import com.baibian.fragment.messy_fiction.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageAndNotificationActivity extends MyBaseActivity implements MessageFragment.OnListFragmentInteractionListener, FriendsFragment.OnFragmentInteractionListener, ChatFragment.OnListFragmentInteractionListener, GroupChatFragment.OnFragmentInteractionListener{

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private List<String> mIndicatorTexts;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mPagerAdapter;

    public static List<MultipleTypes> mMessageData = new ArrayList<>();
    public static List<String> mFriendsLeftDatea = new ArrayList<>();
    public static List<MessageFriendRightItem> mFriendsRightData = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAllData();
    }

    private void clearAllData() {
        mMessageData.clear();
        mFriendsLeftDatea.clear();
        mFriendsRightData.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((AppApplication)getApplication()).isNightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_message_and_notification);

        initMessageData();
        initFriendsData();

        mTabLayout = (TabLayout) findViewById(R.id.messy_fiction_tab);
        mViewPager = (ViewPager) findViewById(R.id.messy_fiction_view_pager);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        mIndicatorTexts = new ArrayList<>();
        mFragments = new ArrayList<>();

        mIndicatorTexts.add("消息");
        mIndicatorTexts.add("好友");
        mIndicatorTexts.add("聊天");
        mIndicatorTexts.add("群聊");

        mFragments.add(MessageFragment.newInstance(1));
        mFragments.add(FriendsFragment.newInstance());
        mFragments.add(ChatFragment.newInstance(1));
        mFragments.add(GroupChatFragment.newInstance());
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mIndicatorTexts.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initFriendsData() {
        for (int i = 0; i < 5; i++){
            mFriendsLeftDatea.add("哈哈哈督察小组");
        }
    }

    private void initMessageData() {
        for (int i = 0; i < 5; i++){
            mMessageData.add(new MessageDetailItem());
        }
        for (int i = 0; i < 5; i++){
            mMessageData.add(new MessageGeneralItem());
        }
        mMessageData.add(new MessageSolidItem());
        mMessageData.add(new MultipleTypes() {
            @Override
            public int getType() {
                return -10;
            }
        });
    }


    @Override
    public void onListFragmentInteraction(MultipleTypes item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(MessageChatItemContent.MessageChatItem item) {

    }

    @Override
    public void onFragmentInteraction(MultipleTypes typeItem) {

    }
}
