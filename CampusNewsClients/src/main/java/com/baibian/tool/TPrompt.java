package com.baibian.tool;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;


/**
 * 自定义Toast
 */
public class TPrompt extends Toast {
    private Context mContext;
//    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private TextView chapterNameTV;
    private LinearLayout rl_root;
    private Handler handler = new Handler();


    public TPrompt(Context context) {
        super(context);
        this.mContext = context;
//        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(mContext, R.anim.toast_anim_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(mContext, R.anim.toast_anim_out);
        initView();
    }

    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.book_reading_toast,null);
        rl_root = (LinearLayout) view.findViewById(R.id.toast_custom_layout);
        chapterNameTV = (TextView) view.findViewById(R.id.tv_toast);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(Toast.LENGTH_LONG);
        setView(view);
    }

    /**
     * 文字提示
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (chapterNameTV != null) {
            chapterNameTV.setText(msg);
            TranslateAnimation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
            );
            animation.setDuration(1000);
            rl_root.setVisibility(View.VISIBLE);
            rl_root.startAnimation(animation);
            show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rl_root.setVisibility(View.INVISIBLE);
                    rl_root.startAnimation(mModalOutAnim);
                }
            }, 3000);

        }
    }

}
