package com.baibian.fragment.messy_fiction.dummy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baibian.tool.RecyclerViewCommonTool.CommonAdapter;
import com.baibian.tool.RecyclerViewCommonTool.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/8/29.
 */

public class MyVerticalListFragment<T> extends Fragment {
    public static final String ARGUMENT_LIST = "argument_list";
    public static final String ARGUMENT_ID = "argument_id";
    private List<T> tList;
    private int layoutId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            //TODO with data initialization
            tList = (List<T>) (bundle.getParcelableArrayList(ARGUMENT_LIST).get(0));
            layoutId = bundle.getInt(ARGUMENT_ID);
        }
    }

    public static<T> MyVerticalListFragment newInstance(List<T> outList, int layoutId) {
        
        Bundle args = new Bundle();
        ArrayList list = new ArrayList();
        list.add(outList);
        args.putParcelableArrayList(ARGUMENT_LIST, list);
        args.putInt(ARGUMENT_ID, layoutId);
        MyVerticalListFragment fragment = new MyVerticalListFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerView mRecyclerView = new RecyclerView(getActivity());
        mRecyclerView.setAdapter(new CommonAdapter<T>(getActivity(), layoutId, tList){
            @Override
            public void convert(ViewHolder holder, T t) {

            }
        });
        return mRecyclerView;
    }

}
