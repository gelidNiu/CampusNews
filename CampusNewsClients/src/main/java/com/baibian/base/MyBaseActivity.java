package com.baibian.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baibian.R;
import com.baibian.activity.UsersImformationActivity;
import com.baibian.activity.user_drawer.users_information.EditPortraitActivity;
import com.baibian.app.AppApplication;
import com.baibian.listener.ReceiverTasksHelper;
import com.baibian.receiver.TasksReceiver;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Ellly on 2017/9/4.
 */

public class MyBaseActivity extends AppCompatActivity {
    TasksReceiver mReceiver = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (((AppApplication)getApplication()).isNightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).init();
    }

    public Bitmap registerImageChangeReciever(){
        final Bitmap[] mBitmap = new Bitmap[1];
        mReceiver = new TasksReceiver();
        mReceiver.setImageLoadingHelper(new ReceiverTasksHelper() {
            @Override
            public void doTasks() {
                mBitmap[0] = EditPortraitActivity.getSaveImageShared(UsersImformationActivity.FILE_NAME_PORTRAIT);
            }
        });
        return mBitmap[0];
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        if (mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    public void transitToAnotherActivity(Class<?> intentClass){
        startActivity(new Intent(this, intentClass));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
