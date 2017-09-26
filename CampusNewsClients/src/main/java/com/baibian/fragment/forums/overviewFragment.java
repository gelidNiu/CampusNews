package com.baibian.fragment.forums;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baibian.R;
import com.baibian.activity.PublishActivity;

/**
 * Created by XZY on 2017/4/15.ËéÆ¬ÒÑ¾­±»·Ï³ý
 */
public class overviewFragment extends Fragment{
private Button publish_bt;
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.forums_argument_view, container, false);
        return mView;
    }

}
