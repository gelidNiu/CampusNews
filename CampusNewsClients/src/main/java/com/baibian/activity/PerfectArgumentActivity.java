package com.baibian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.fragment.forums.PerfectArgumentFragment;
import com.baibian.fragment.forums.PerfectObjectionListFragment;
import com.baibian.fragment.forums.PerfectReasonFragment;

public class PerfectArgumentActivity extends AppCompatActivity implements View.OnClickListener, PerfectReasonFragment.ChooseTvClickListener, PerfectObjectionListFragment.ChooseTvClickListener2 {
    private TextView reason_confirm_tv;
    private PerfectArgumentFragment perfectArgumentFragment;
    private PerfectObjectionListFragment perfectObjectionListFragment;
    private PerfectReasonFragment perfectReasonFragment;
    private final int OLDOBJECTION = 0;
    private final int NEWOBJECTION = 1;
    private int flag = OLDOBJECTION;//记录状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfect_argument_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        hideBottomUIMenu();//�������ⰴť
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            perfectObjectionListFragment = new PerfectObjectionListFragment();
            perfectArgumentFragment = new PerfectArgumentFragment();
            perfectReasonFragment = new PerfectReasonFragment();
            transaction.add(R.id.reason_fragment, perfectReasonFragment, "perfectReasonFragment");
            transaction.add(R.id.argument_content, perfectArgumentFragment, "perfectArgumentFragment");
            transaction.commit();
        } else {
            /**
             * 之所以reasonFragment的内容可以被保存，是因为其本身调用的代码有存储功能,如setText由内部存储
             */
            perfectReasonFragment = (PerfectReasonFragment) getSupportFragmentManager().findFragmentByTag("perfectReasonFragment");//这里perfectReasonFragment在perfectObjectionlistFragment创建时被销毁了
            perfectArgumentFragment = (PerfectArgumentFragment) getSupportFragmentManager().findFragmentByTag("perfectArgumentFragment");
            perfectObjectionListFragment = (PerfectObjectionListFragment) getSupportFragmentManager().findFragmentByTag("perfectObjectionListFragment");
            if (perfectObjectionListFragment == null) {
                perfectObjectionListFragment = new PerfectObjectionListFragment();
            }

        }
        initView();
    }

    /*
**???????????????????
*/
    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            //??????????|???????????
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar//?????????????
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//?????
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void initView() {
        View view = View.inflate(getApplicationContext(), R.layout.fragment_perfect_reason, null);
        perfectObjectionListFragment.setChooseTvClickListener2(this);
        perfectReasonFragment.setChooseTvClickListener(this);
        reason_confirm_tv = (TextView) view.findViewById(R.id.reason_confirm_tv);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reason_confirm_tv:
                if (flag == OLDOBJECTION) {
                    flag = NEWOBJECTION;
                    reason_confirm_tv.setText(R.string.choose_new_objection);
                } else {
                    flag = OLDOBJECTION;
                    reason_confirm_tv.setText(R.string.choose_exist_objection);
                }
                break;
        }
    }

    /**
     * 隐患，可能会无线堆栈，内存泄漏
     */
    public void onChooseTvClick() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentByTag("perfectObjectionListFragment")!=null)
        {
            perfectObjectionListFragment=(PerfectObjectionListFragment)getSupportFragmentManager().findFragmentByTag("perfectObjectionListFragment");//如果上次加载过perfectObjectionListFragment，重载它
        }
        if(!perfectObjectionListFragment.isAdded()){
        transaction.add(R.id.reason_fragment, perfectObjectionListFragment, "perfectObjectionListFragment");}
        else{
            transaction.hide(perfectReasonFragment).show(perfectObjectionListFragment);
        }
        reason_confirm_tv.setText(R.string.choose_new_objection);
        transaction.commit();//设置transaction为全局变量，只能提交一次
    }

    public void onChooseTvClick2() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentByTag("perfectReasonFragment")!=null)
        {
            perfectReasonFragment=(PerfectReasonFragment)getSupportFragmentManager().findFragmentByTag("perfectReasonFragment");
        }
        transaction.hide(perfectObjectionListFragment).show(perfectReasonFragment);
        transaction.commit();
    }
}
