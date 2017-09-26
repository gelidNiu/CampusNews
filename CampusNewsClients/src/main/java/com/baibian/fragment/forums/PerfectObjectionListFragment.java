package com.baibian.fragment.forums;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.adapter.ObjectionListAdapter;
import com.baibian.information.Objection;

import java.util.ArrayList;
import java.util.List;

/**
 * 已有异议列表界面
 */
public class PerfectObjectionListFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mObjecitonRecyclerView;//异议recyclerview
    private ObjectionListAdapter mObjectionListAdapter;//异议列表的适配器
    private LayoutInflater mInflater;
    private View mView;
    private List<Objection> objectionList = new ArrayList<>();
    private Context mContext;
    private TextView reason_change_tv;
    private ImageView objectionlist_back_tv;
    private ChooseTvClickListener2 chooseTvClickListener2;
    private int choosePostion=-1;
    public interface ChooseTvClickListener2 {
        void onChooseTvClick2();
    }

    public void setChooseTvClickListener2(ChooseTvClickListener2 chooseTvClickListener2) {
        this.chooseTvClickListener2 = chooseTvClickListener2;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        setRetainInstance(true);//直接跳过onCreate方法
        super.onCreate(onSavedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            this.choosePostion=savedInstanceState.getInt("choosePosition");//获取
        }
        mInflater = inflater;
        mView = inflater.inflate(R.layout.fragment_perfect_objection_list, container, false);
        initObjection();
        initView();
        mObjecitonRecyclerView.setAdapter(mObjectionListAdapter);
        mObjecitonRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//设置布局
        System.out.println("perfectObjectionListFragment onCreate");
        return mView;
    }

    public void initView() {
        mObjecitonRecyclerView = (RecyclerView) mView.findViewById(R.id.objection_list);
        mObjectionListAdapter = new ObjectionListAdapter(mContext, objectionList, mObjecitonRecyclerView,choosePostion);//这个用哪个context待定??
        reason_change_tv = (TextView) mView.findViewById(R.id.reason_change_tv);
        objectionlist_back_tv = (ImageView) mView.findViewById(R.id.objectionlist_back_img);
        objectionlist_back_tv.setOnClickListener(this);
        reason_change_tv.setOnClickListener(this);
    }

    /**
     * 初始化异议内容
     */
    public void initObjection() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reason_change_tv:
                chooseTvClickListener2.onChooseTvClick2();
                break;
            case R.id.objectionlist_back_img:
                getActivity().finish();
                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
            outState.putInt("choosePosition",mObjectionListAdapter.getChoosePosition());//保存选中的item的位置
    }

}
