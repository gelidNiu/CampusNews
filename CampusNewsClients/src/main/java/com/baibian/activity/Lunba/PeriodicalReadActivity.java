package com.baibian.activity.Lunba;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.bean.ReadSettingManager;
import com.baibian.view.LunBa.SelectableTextView;

import java.util.ArrayList;
import java.util.List;

import static com.baibian.R.color.green_dark;
import static com.baibian.R.color.white_dark;


public class PeriodicalReadActivity extends AppCompatActivity implements
        SelectableTextView.CustomActionMenuCallBack,View.OnClickListener{
    /***************参数*****************/
    private boolean isShow = false;//显示弹窗判断标志
    private boolean isNightMode = false;//夜间模式判断标志
    private int frontSize = 16;//字体
    private int background= white_dark;

    /***************控件*****************/
    PopupWindow title;//标题弹窗
    PopupWindow menu;//底部菜单弹窗
    PopupWindow setting;//底部菜单中的设置弹窗
    PopupWindow correction;//纠错弹窗
    PopupWindow sharing_center;//分享弹窗
    PopupWindow sharing_bottom;//分享内容弹窗

    LayoutInflater inflaterTitle;//标题弹窗加载器
    LayoutInflater inflaterMenu;//底部菜单弹窗加载器
    LayoutInflater inflaterSetting;//底部菜单中的字体设置弹窗加载器

    SelectableTextView selectableTextView;//期刊内容TextView



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        //得到存储的信息
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        frontSize=ReadSettingManager.getInstance().getTextSize();
        background=ReadSettingManager.getInstance().getBackground();

        /***************初始化*****************/
        selectableTextView = (SelectableTextView) findViewById(R.id.read_pv_page);
        changeDayNightMode();//初始化日夜间模式
        selectableTextView.setTextSize(frontSize);//初始化字体大小
        if(!isNightMode){
            selectableTextView.setBackgroundColor(getResources().getColor(background));//初始化背景颜色
        }
        selectableTextView.clearFocus();
        selectableTextView.setTextJustify(true);
        selectableTextView.postInvalidate();
        selectableTextView.setCustomActionMenuCallBack(this);

        /***************控件*****************/
        //获取LayoutInflater，用于载入layout下的布局文件，类似于findViewById()
        inflaterTitle  = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflaterMenu  = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflaterSetting = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //载入PopupWindow的布局
        View popTitleView = getLayoutInflater().inflate(R.layout.periodical_title, null, false);
        View popMenuView = getLayoutInflater().inflate(R.layout.activity_read_menu, null, false);
        View popSettingView = getLayoutInflater().inflate(R.layout.activity_read_front_setting, null, false);
        //设置PopupWindow的布局
        title =new PopupWindow(popTitleView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,true);
        menu =new PopupWindow(popMenuView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,true);
        setting =new PopupWindow(popSettingView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,true);
        //获取各弹窗控件句柄
        final Button back=(Button) popTitleView.findViewById(R.id.back);//标题弹窗中的返回按钮
        final TextView read_tv_category = (TextView) popMenuView.findViewById(R.id.read_tv_category);//菜单弹窗中的目录按钮
        final TextView read_tv_night_mode = (TextView) popMenuView.findViewById(R.id.read_tv_night_mode);//菜单弹窗中的夜间模式按钮
        final TextView read_tv_setting = (TextView) popMenuView.findViewById(R.id.read_tv_setting);//菜单弹窗中的字号按钮

        final TextView read_setting_tv_font_minus = (TextView) popSettingView.findViewById(R.id.read_setting_tv_font_minus);//字号弹窗中的Aa+按钮
        final TextView read_setting_tv_font_plus = (TextView) popSettingView.findViewById(R.id.read_setting_tv_font_plus);//字号弹窗中的Aa-按钮
        final TextView read_setting_tv_font = (TextView) popSettingView.findViewById(R.id.read_setting_tv_font);//字号弹窗中的字号显示框
        read_setting_tv_font.setText(frontSize+"");//初始化字号弹窗中的字号显示框中的内容
        final RadioGroup backgroundColor = (RadioGroup) popSettingView.findViewById(R.id.read_setting_background);//字号弹窗中的字号显示框

        /***************具体实现逻辑*****************/
        //初始化菜单弹窗中的夜间模式按钮的图案
        if(isNightMode){
            Drawable drawable=getResources().getDrawable(R.drawable.ic_read_menu_night);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            read_tv_night_mode.setCompoundDrawables(null,drawable,null,null);
            read_tv_night_mode.setText("夜间");
        }

        //点击期刊内容出现标题、菜单弹窗，同时实现再次点击期刊内容时，所有弹窗消失
        selectableTextView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(isShow == true)
                {
                    title.dismiss();
                    menu.dismiss();
                    isShow = false;
                }
                else
                {
                    menu.showAtLocation(v, Gravity.BOTTOM,0,0); //设置menu位置在底部
                    menu.setFocusable(false);
                    menu.setOutsideTouchable(true);
                    menu.update();

                    title.showAtLocation(v, Gravity.TOP,0,0); //设置title位置在顶部
                    title.setFocusable(false);
                    title.setOutsideTouchable(true);
                    title.update();
                    isShow = true;
                }
                if (correction != null && correction.isShowing()) {
                    correction.dismiss();
                }
                if (setting != null && setting.isShowing()) {
                    setting.dismiss();
                }
                if (sharing_center != null && sharing_center.isShowing()) {
                    sharing_center.dismiss();
                }
                if (sharing_bottom != null && sharing_bottom.isShowing()) {
                    sharing_bottom.dismiss();
                }
            }
        });


        //标题——返回上一界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //菜单——目录（未完成）
        read_tv_category.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Toast.makeText(PeriodicalReadActivity.this,"category", Toast.LENGTH_SHORT).show();
            }
        });


        //菜单——夜间模式
        read_tv_night_mode.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                isNightMode=!isNightMode;//修改标志
                if (isNightMode){
                    Drawable drawable=getResources().getDrawable(R.drawable.ic_read_menu_morning);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    read_tv_night_mode.setCompoundDrawables(null,drawable,null,null);
                    read_tv_night_mode.setText("日间");
                }else{
                    Drawable drawable=getResources().getDrawable(R.drawable.ic_read_menu_night);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    read_tv_night_mode.setCompoundDrawables(null,drawable,null,null);
                    read_tv_night_mode.setText("夜间");
                }
                changeDayNightMode();//切换模式
                ReadSettingManager.getInstance().setNightMode(isNightMode);//保存夜间模式信息

            }
        });

        //菜单——字体设置
        read_tv_setting.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                View view =selectableTextView;
                setting.showAtLocation(view, Gravity.BOTTOM,0,0); //设置menu位置在底部
                setting.setFocusable(false);
                setting.setOutsideTouchable(true);
                setting.update();
            }
        });


        //菜单——设置——Aa+
        read_setting_tv_font_minus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                frontSize=Integer.parseInt(read_setting_tv_font.getText().toString());//获取字号信息
                //最小字号为14
                if (frontSize>14){
                    frontSize--;
                    selectableTextView.setTextSize(frontSize);//更改内容字号
                    read_setting_tv_font.setText(frontSize+"");//更改字号弹窗中的字号显示框中的内容
                    ReadSettingManager.getInstance().setTextSize(frontSize);//保存字号信息
                }

            }
        });
        //菜单——设置——Aa-
        read_setting_tv_font_plus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                frontSize=Integer.parseInt(read_setting_tv_font.getText().toString());
                //最大字号为18
                if (frontSize<18){
                    frontSize++;//字号加
                    selectableTextView.setTextSize(frontSize);//更改内容字号
                    read_setting_tv_font.setText(frontSize+"");//更改字号弹窗中的字号显示框中的内容
                    ReadSettingManager.getInstance().setTextSize(frontSize);//保存字号信息
                }

            }
        });

        //菜单——设置——背景设置
        backgroundColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //点击背景设置后默认切换为日间模式
                isNightMode=false;
                Drawable drawable=getResources().getDrawable(R.drawable.ic_read_menu_night);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                read_tv_night_mode.setCompoundDrawables(null,drawable,null,null);
                read_tv_night_mode.setText("夜间");
                ReadSettingManager.getInstance().setNightMode(isNightMode);
                //根据所选radioButton设置背景颜色
                int radioButtonId = group.getCheckedRadioButtonId();
                switch (radioButtonId){
                    case R.id.background_1:background=R.color.c1;break;
                    case R.id.background_2:background=R.color.c2;break;
                    case R.id.background_3:background=R.color.c3;break;
                    case R.id.background_4:background=R.color.c4;break;
                    case R.id.background_5:background=R.color.c5;break;
                    case R.id.background_default:background= white_dark;break;
                }
                selectableTextView.setBackgroundColor(getResources().getColor(background));
                ReadSettingManager.getInstance().setBackground(background);//保存背景颜色信息
            }
        });

    }

    //长按绘制期刊内容后弹出的菜单设置
    @Override
    public boolean onCreateCustomActionMenu(SelectableTextView.ActionMenu menu) {
        menu.setMenuItemTextColor(0xffffffff);
        List<String> titleList = new ArrayList<>();
        titleList.add("纠错");//增加纠错选项
        titleList.add("分享");//增加分析选项
        menu.addCustomMenuItem(titleList);
        return false;
    }

    @Override
    public void onCustomActionItemClicked(String itemTitle, String selectedContent) {
        if (itemTitle.equals("纠错")) {
            showCorrectionPopupWindow();

        }
        if (itemTitle.equals("分享")) {
            showSharingPopupWindow();
        }
    }


    //分享
    private void showSharingPopupWindow() {
        View contentView_center = LayoutInflater.from(PeriodicalReadActivity.this).inflate(R.layout.activity_read_sharing_content, null);
        View contentView_bottom = LayoutInflater.from(PeriodicalReadActivity.this).inflate(R.layout.activity_read_sharing, null);
        sharing_center = new PopupWindow(contentView_center);
        sharing_bottom = new PopupWindow(contentView_bottom);
        sharing_center.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        sharing_center.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        sharing_bottom.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        sharing_bottom.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        Button cancel = (Button)contentView_center.findViewById(R.id.btn_cancel);
        TextView sharing_text = (TextView)contentView_center.findViewById(R.id.tv_sharing_text);
        TextView sharing_title = (TextView)contentView_center.findViewById(R.id.tv_sharing_title);
        ImageView sharing_cover = (ImageView)contentView_center.findViewById(R.id.iv_sharing_cover);

        ImageView Share_to_Weixin = (ImageView)contentView_bottom.findViewById(R.id.iv_WeiXin);
        ImageView Share_to_Moments = (ImageView)contentView_bottom.findViewById(R.id.iv_Moments);
        ImageView Share_to_Weibo = (ImageView)contentView_bottom.findViewById(R.id.iv_WeiBo);

        sharing_text.setText(selectableTextView.getCopy());

        cancel.setOnClickListener(this);
        Share_to_Weixin.setOnClickListener(this);
        Share_to_Moments.setOnClickListener(this);
        Share_to_Weibo.setOnClickListener(this);

        View v =selectableTextView;
        sharing_center.showAtLocation(v,Gravity.CENTER,0,0);
        sharing_center.setOutsideTouchable(true);
        sharing_center.setFocusable(false);
        sharing_bottom.showAtLocation(v,Gravity.BOTTOM,0,0);
        sharing_bottom.setOutsideTouchable(true);
        sharing_bottom.setFocusable(false);

    }



    //纠错
    private void showCorrectionPopupWindow() {
        View contentView = LayoutInflater.from(PeriodicalReadActivity.this).inflate(R.layout.activity_read_correction_menu, null);
        correction = new PopupWindow(contentView);
        correction.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        correction.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv1 = (TextView)contentView.findViewById(R.id.menu_omissions);
        TextView tv2 = (TextView)contentView.findViewById(R.id.menu_incompletion);
        TextView tv3 = (TextView)contentView.findViewById(R.id.menu_disorder);
        TextView tv4 = (TextView)contentView.findViewById(R.id.menu_garbled);
        TextView tv5 = (TextView)contentView.findViewById(R.id.menu_inconsequence);
        TextView tv6 = (TextView)contentView.findViewById(R.id.menu_other);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        View v =selectableTextView;
        correction.showAtLocation(v,Gravity.CENTER,0,0);
        correction.setOutsideTouchable(true);
        correction.setFocusable(false);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.menu_omissions:
                Toast.makeText(PeriodicalReadActivity.this, "缺字漏字", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.menu_incompletion:
                Toast.makeText(PeriodicalReadActivity.this, "内容不完整", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.menu_disorder:
                Toast.makeText(PeriodicalReadActivity.this, "章节排序混乱", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.menu_garbled:
                Toast.makeText(PeriodicalReadActivity.this, "广告乱码", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.menu_inconsequence:
                Toast.makeText(PeriodicalReadActivity.this, "文不对题", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.menu_other:
                Toast.makeText(PeriodicalReadActivity.this, "其它", Toast.LENGTH_SHORT).show();
                correction.dismiss();break;
            case R.id.btn_cancel:
                sharing_center.dismiss();sharing_bottom.dismiss();
                break;
            case R.id.iv_WeiXin:
                Toast.makeText(PeriodicalReadActivity.this, "分享到微信", Toast.LENGTH_SHORT).show();
                sharing_center.dismiss();sharing_bottom.dismiss();
            case R.id.iv_Moments:
                Toast.makeText(PeriodicalReadActivity.this, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                sharing_center.dismiss();sharing_bottom.dismiss();
            case R.id.iv_WeiBo:
                Toast.makeText(PeriodicalReadActivity.this, "分享到微博", Toast.LENGTH_SHORT).show();
                sharing_center.dismiss();sharing_bottom.dismiss();
        }
    }


    //日夜间模式切换
    private void changeDayNightMode(){
        if (isNightMode) {
            selectableTextView.setBackgroundColor(getResources().getColor(green_dark));
            selectableTextView.setTextColor(getResources().getColor(white_dark));
        } else {
            selectableTextView.setBackgroundColor(getResources().getColor(white_dark));
            selectableTextView.setTextColor(getResources().getColor(green_dark));
        }
    }

}






