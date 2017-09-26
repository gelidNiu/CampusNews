package com.baibian.activity.user_drawer.user_setting;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.base.MyBaseActivity;
import com.baibian.tool.ToastTools;
import com.baibian.view.reusable_view.CustomBottomUpDialog;
import com.baibian.view.reusable_view.EmphasisRelativeLayout;

public class UserSettingActivity extends MyBaseActivity implements View.OnClickListener{

    private EmphasisRelativeLayout mTextSizeRelativeLayout;
    private EmphasisRelativeLayout mSendRelativeLayout;
    private EmphasisRelativeLayout mResponseAndHelpLayout;
    private RelativeLayout mMessageSetLayout;
    private EmphasisRelativeLayout mRelationLayout;
    private int mChosenTextSizeThemeId = -1;
    private CustomBottomUpDialog mTextChangeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        init();

    }

    private void init() {
        mResponseAndHelpLayout = (EmphasisRelativeLayout) findViewById(R.id.setting_response_and_help);
        mSendRelativeLayout = (EmphasisRelativeLayout) findViewById(R.id.setting_send_set);
        mTextSizeRelativeLayout = (EmphasisRelativeLayout) findViewById(R.id.setting_modify_text_size);
        mMessageSetLayout = (RelativeLayout) findViewById(R.id.setting_message_set);
        mRelationLayout = (EmphasisRelativeLayout) findViewById(R.id.setting_relation);
        mRelationLayout.setOnClickListener(this);
        mMessageSetLayout.setOnClickListener(this);
        mSendRelativeLayout.setOnClickListener(this);
        mTextSizeRelativeLayout.setOnClickListener(this);
        mResponseAndHelpLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_modify_text_size:
                mTextChangeDialog = new CustomBottomUpDialog(this, R.layout.user_setting_change_text_dialog_layout) {
                    RadioGroup mGroup;
                    TextView mPositiveBtn;
                    TextView mNegativeBtn;

                    RadioButton mSmallBtn;
                    RadioButton mMiddlBtn;
                    RadioButton mLargeBtn;
                    RadioButton mSuperLargeBtn;

                    @Override
                    public void initView(CustomBottomUpDialog dialog) {
                        mGroup = (RadioGroup) dialog.findViewById(R.id.user_setting_c_t_d_radio_group);
                        mPositiveBtn = (TextView) dialog.findViewById(R.id.user_setting_c_t_d_ensure_btn);
                        mNegativeBtn = (TextView) dialog.findViewById(R.id.user_setting_c_t_d_cancel_btn);

                        mPositiveBtn.setOnClickListener(this);
                        mNegativeBtn.setOnClickListener(this);
                        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                 switch (checkedId){
                                     case R.id.user_setting_c_t_d_radio_group_btn_small:
                                         //预备通过改变theme来改变全局字体大小
                                         //这里随便给了个style id示意
//                                         mChosenTextSizeThemeId = R.style.AppTheme_;
                                         break;
                                     case R.id.user_setting_c_t_d_radio_group_btn_middle:
                                         break;
                                     case R.id.user_setting_c_t_d_radio_group_btn_large:
                                         break;
                                     case R.id.user_setting_c_t_d_radio_group_btn_super_large:
                                         break;
                                 }
                            }
                        });
                    }

                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.user_setting_c_t_d_ensure_btn:
                                ToastTools.ToastShow("" + mChosenTextSizeThemeId);
                                mTextChangeDialog.dismiss();
                                break;
                            case R.id.user_setting_c_t_d_cancel_btn:
                                mTextChangeDialog.dismiss();
                                break;
                        }
                    }
                };
                mTextChangeDialog.setEmergeMode(false);
                mTextChangeDialog.show();
            break;
            case R.id.setting_send_set:
                transitToAnotherActivity(SettingPushSendActivity.class);
                break;
            case R.id.setting_response_and_help:
                transitToAnotherActivity(SettingResponseAndHelpActivity.class);
                break;
            case R.id.setting_message_set:
                //can be replaced with declarations above.
                transitToAnotherActivity(SettingMessageActivity.class);
                break;
            case R.id.setting_relation:
                transitToAnotherActivity(SettingAboutLunDao.class);
                break;
        }
    }
}
