package com.baibian.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baibian.R;
import com.baibian.activity.MainActivity;
import com.baibian.activity.login.Login4Activity;
import com.baibian.activity.login.registerActivity;

import java.util.List;

/**
 * ���������Viewpager��adapter
 */
public class Guide_adapter extends PagerAdapter {
    public List<View> Viewlist;
    public Activity activity;
    private Button guide_register_btn;
    private Button guide_login_btn;
    private Button guide_visitor_btn;

    public Guide_adapter(List<View> Viewlist, Activity activity) {
        this.activity = activity;
        this.Viewlist = Viewlist;
    }

    /**
     * ����ҳ����Ŀ
     */
    public int getCount() {
        return Viewlist.size();
    }


    /**
     * View�Ƿ����Զ���
     */
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * ʵ����һ��ҳ��
     */
    // public Object instantiateItem(ViewGroup container,int position){
    //    container.addView(Viewlist.get(position));
    //    return Viewlist.get(position);
    //  }

    /**
     * ����һ��ҳ��
     */
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(Viewlist.get(position));

    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {


        ((ViewPager) arg0).addView(Viewlist.get(arg1), 0);
        /**
         * ���������һ������ʱ��ʵ����������ť��
         */
        if (arg1 == Viewlist.size() - 1) {
            guide_login_btn = (Button) arg0.findViewById(R.id.guide_login_btn);
            guide_register_btn = (Button) arg0.findViewById(R.id.guide_register_btn);
            guide_visitor_btn = (Button) arg0.findViewById(R.id.guide_visitor_btn);
            /**
             * ��¼��ť�������¼����
             */
            guide_login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, Login4Activity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
            /**
             * ע�ᰴť������ע�����
             */
            guide_register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, registerActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
            /**
             * �ο�ģʽ��ť������������
             */
            guide_visitor_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
        }

        return Viewlist.get(arg1);
    }


}