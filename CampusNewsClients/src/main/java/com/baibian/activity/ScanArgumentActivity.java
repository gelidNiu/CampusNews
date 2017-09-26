package com.baibian.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.view.MyScrollView;

import java.io.UnsupportedEncodingException;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class ScanArgumentActivity extends AppCompatActivity implements MyScrollView.ScrollViewListener, View.OnClickListener {
    private MyScrollView original_scrollView;//原论点
    private MyScrollView change_scrollView;//改变后的论点
    private EditText original_argument_edt;//原论点编辑框
    private EditText change_argument_edt;//改变论点编辑框
    private int word_length;//计算每次输入的字数
    private Editable original_editable;//EditText插入功能所需要的对象
    private Editable change_editable;
    private int i;
    private StringBuilder stringBuilder;
    private String spaceString;
    private int beforeNum;//输入前输入框的总字数
    private int afterNum;//输入后的输入框的总字数
    private String original_temp;//存储原文的版本内容方便转化为全角
    private String change_temp;//存储改变后的版本内容方便转化为全角
    private ImageView back_img;//返回
    private ImageView edit_img;//编辑论点
    private TextView delete_tv;//删除自己的论点
    private ImageView jump_img;//跳转到下一个论点
    private ImageView praise_img;//点赞
    private TextView praise_num;//点赞数量textview
    private int praiseNum;//点赞数量
    private boolean approveState = false;
    private boolean opposeState = false;
    private Intent
            perfectIntent;
    private PopupWindow praisePopupwindow;
    private View popupWindowContentView;
    private LinearLayout praiseLayout;
    private TextView argumenter;
    private ImageView pop_approve_img;
    private ImageView pop_oppose_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_argument_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        hideBottomUIMenu();//隐藏虚拟按钮
        perfectIntent = new Intent(ScanArgumentActivity.this, PerfectArgumentActivity.class);
        initView();
    }

    /*
**�������ⰴť��״̬���ķ�װ
 */
    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            //����ķ�����|����ͬʱѡ����
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar//�����������ⰴť
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//����ʽ
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initView() {
        System.out.println("scanactivity oncreate");
        popupWindowContentView = LayoutInflater.from(ScanArgumentActivity.this).inflate(R.layout.praise_popwindow, null);
        praisePopupwindow = new PopupWindow(popupWindowContentView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, true);
        praisePopupwindow.setOutsideTouchable(true);//设置外部可点击
        //praisePopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        praiseLayout = (LinearLayout) findViewById(R.id.scan_praise_layout);
        praiseLayout.setOnClickListener(this);//对整个点赞框进行监听
        pop_approve_img = (ImageView) popupWindowContentView.findViewById(R.id.pop_approve_img);
        pop_approve_img.setOnClickListener(this);
        pop_oppose_img = (ImageView) popupWindowContentView.findViewById(R.id.pop_oppose_img);
        pop_oppose_img.setOnClickListener(this);
        stringBuilder = new StringBuilder();
        original_scrollView = (MyScrollView) findViewById(R.id.original_argument);
        change_scrollView = (MyScrollView) findViewById(R.id.change_argument);
        argumenter = (TextView) findViewById(R.id.argumenter);
        original_scrollView.setScrollViewListener(this);
        change_scrollView.setScrollViewListener(this);
        original_argument_edt = (EditText) findViewById(R.id.original_argument_edt);
        change_argument_edt = (EditText) findViewById(R.id.change_argument_edt);
        change_argument_edt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        back_img = (ImageView) findViewById(R.id.argument_back_img);
        edit_img = (ImageView) findViewById(R.id.arument_edit_img);
        delete_tv = (TextView) findViewById(R.id.argument_delete_tv);
        praise_img = (ImageView) findViewById(R.id.argument_praise_img);
        jump_img = (ImageView) findViewById(R.id.jump_to_nextpage_img);
        /*
        **监听改变后的论点内容变化，这个监听是你每确定输入一个字符就检测一次.editText一个中文字符就占一个光标
         */
        back_img.setOnClickListener(this);
        edit_img.setOnClickListener(this);
        delete_tv.setOnClickListener(this);
        praise_img.setOnClickListener(this);
        jump_img.setOnClickListener(this);
    }

    /*
    **实现同步滚动的scrollview
     */
    public void onScrollChanged(MyScrollView myScrollView, int x, int y, int oldx, int oldy) {
        if (myScrollView == original_scrollView) {
            change_scrollView.scrollTo(x, y);
        } else if (myScrollView == change_scrollView) {
            original_scrollView.scrollTo(x, y);
        }
    }


    // 半角转化为全角的方法，解决中文与英文数字占位不同的问题
    public String toSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /*
    **监听事件
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.argument_back_img:
                finish();//返回上一个界面
                break;
            case R.id.argument_praise_img:
                break;
            case R.id.jump_to_nextpage_img:
                //跳转另一个论点，待完善
                break;
            case R.id.argument_delete_tv:
                //删除自己的论点的，待完善
                break;
            case R.id.arument_edit_img:
                //编辑自己的完善版本，待完善
                startActivity(perfectIntent);//启动完善活动
                break;
            case R.id.scan_praise_layout:
                showPopupwindow();
                hideBottomUIMenu();
                break;
            case R.id.pop_approve_img:
                if (!approveState) {
                    approveState = true;
                    pop_approve_img.setImageResource(R.mipmap.have_praised);
                } else {
                    approveState = false;
                    pop_approve_img.setImageResource(R.mipmap.not_praised);
                }
                break;
            case R.id.pop_oppose_img:
                if (!opposeState) {
                    opposeState = true;
                    pop_oppose_img.setImageResource(R.mipmap.have_opposed);
                } else {
                    opposeState = false;
                    pop_oppose_img.setImageResource(R.mipmap.not_opposed);
                }
                break;
            default:
        }
    }

    public void showPopupwindow() {
        praisePopupwindow.showAsDropDown(delete_tv);//测试放在删除两个字下面

    }
}

