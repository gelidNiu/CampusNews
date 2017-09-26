package com.baibian.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baibian.R;
import com.baibian.activity.setting.SettingsActivity;

import java.util.List;


public class Users_Viwepager_Adapter extends PagerAdapter {

    /**
     *
     */
        public List<View > Viewlist;
        public Activity activity;
        private LinearLayout settting_linearlayout;
        public  Users_Viwepager_Adapter(List<View>Viewlist, Activity activity){
            this.activity=activity;
            this.Viewlist=Viewlist;
        }
        /**
         * ??????????
         */
        public  int getCount(){
            return Viewlist.size();
        }



        /**
         *View??????????
         */
        public boolean isViewFromObject(View arg0, Object arg1){
            return  arg0==arg1;
        }

        /**
         * ???????????
         */
        // public Object instantiateItem(ViewGroup container,int position){
        //    container.addView(Viewlist.get(position));
        //    return Viewlist.get(position);
        //  }
        /**
         * ??????????
         */
        public void destroyItem(ViewGroup container, int position, Object object){

            container.removeView(Viewlist.get(position));
        }
        @Override
        public Object instantiateItem(View arg0,int arg1){
            ((ViewPager) arg0).addView(Viewlist.get(arg1), 0);
            if (arg1== 0){


            }

            /**
             * ?????????????????
             */
           else if (arg1== 1){
                settting_linearlayout=(LinearLayout) arg0.findViewById(R.id.settting_layout);
                settting_linearlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity,SettingsActivity.class));
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }

            return Viewlist.get(arg1);
        }


    }