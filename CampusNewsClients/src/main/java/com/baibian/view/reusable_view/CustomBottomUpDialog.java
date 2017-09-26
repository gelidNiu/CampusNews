package com.baibian.view.reusable_view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.baibian.R;

/**
 * Created by Ellly on 2017/8/18.
 * �Զ�����µ���dialog��Ĭ��Ϊtrueʱ�� falseʱΪ��ͨ�ĵ���dialog��
 */

public abstract class CustomBottomUpDialog extends Dialog implements View.OnClickListener{
    private int mLayoutId;
    private Context mContext;
    private View mContentView;
    private Window mWindow;
    private boolean isBottomUp = true;

    public CustomBottomUpDialog(@NonNull Context context, int layoutResId) {
        super(context, R.style.CustomDialogTheme);
        mContext = context;
        mLayoutId = layoutResId;
        mWindow = getWindow();
    }

    public CustomBottomUpDialog(@NonNull Context context, @StyleRes int themeResId, int layoutResId) {
        super(context, themeResId);
        mLayoutId = layoutResId;
    }

    protected CustomBottomUpDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setEmergeMode(boolean isBottomUp){
        this.isBottomUp = isBottomUp;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        mContentView = LayoutInflater.from(getContext()).inflate(mLayoutId, null);
        if (isBottomUp) {
            mWindow.setGravity(Gravity.BOTTOM);
            mWindow.setWindowAnimations(R.style.DialogAnimStyle);
            mWindow.getDecorView().setPadding(0, 0, 0, 0);
            //���window���ڵ�����
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            //���ô��ڿ��Ϊ����ȫ��
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //���ô��ڸ߶�Ϊ��������
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //�����úõ�����set��ȥ
            mWindow.setAttributes(lp);
        }
        setContentView(mLayoutId);
        initView(this);
    }

    public abstract void initView(CustomBottomUpDialog dialog);
}
