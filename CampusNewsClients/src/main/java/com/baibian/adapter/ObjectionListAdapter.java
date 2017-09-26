package com.baibian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.information.Objection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ����ǿ on 2017/8/1.
 */

/*
**异议列表
 */
public class ObjectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Objection> objectionList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;//这个mViewHolder实际上是最后一个item的viewHolder
    private ViewHolder chooseViewHolder;//记录选中的viewHolder从而控制选中的item的控件的状态及其他控件，绝妙的想法
    private List<ViewHolder> viewHolderList = new ArrayList<>();//存储所有item的viewHolder
    private RecyclerView mRecyclerView;
    private ViewHolder clickViewHolder;//记录被点击的viewHolder,通过chooseViewHolder需要发表的东西就被缓存下来，神来之笔
    private int firstVisibleItemPositon;//屏幕第一个显示的item的position
    private int choosePosition = -1;//记录被选中的item的位置，未选中时为-1
    static final int SHRING_STATE = 0;//content_tv的收缩状态
    static final int SPREAD_STATE = 1;//content_tv的展开状态

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView head_img;//用户头像
        private TextView time_tv;//异议时间
        private TextView title_tv;//异议标题
        private TextView content_tv;//异议内容
        private TextView name_tv;//异议者昵称
        private ImageView confirm_img;//确认已采纳标记
        private int contentTvState = SHRING_STATE;

        public ViewHolder(View view) {
            super(view);
            head_img = (ImageView) view.findViewById(R.id.objection_person_head_img);
            time_tv = (TextView) view.findViewById(R.id.objection_time_tv);
            title_tv = (TextView) view.findViewById(R.id.objection_title_tv);
            content_tv = (TextView) view.findViewById(R.id.objection_content_tv);
            name_tv = (TextView) view.findViewById(R.id.objection_person_name_tv);
            confirm_img = (ImageView) view.findViewById(R.id.confirm_img);

        }
    }

    public ObjectionListAdapter(Context context, List<Objection> objectionList, RecyclerView mRecyclerView, int choosePosition) {
        this.mContext = context;
        this.objectionList = objectionList;
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerView = mRecyclerView;
        this.choosePosition = choosePosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mViewHolder = new ViewHolder(mInflater.inflate(R.layout.objecion_recyclelist_item, parent, false));
        viewHolderList.add(mViewHolder);//添加viewholder
        System.out.println("onCreate");
        return mViewHolder;

    }

    /*
    **?????????
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewHolder = (ViewHolder) holder;
        mViewHolder.confirm_img.setTag(position);//标记每个item，方便对每个item进行监听
        mViewHolder.content_tv.setTag(position);
        mViewHolder.confirm_img.setOnClickListener(this);
        mViewHolder.content_tv.setOnClickListener(this);
        if ((choosePosition >= 0) && (choosePosition == position)) {
            mViewHolder.confirm_img.setImageResource(R.drawable.objection_choose);//设置上次选中的item
            System.out.println("choose");
        } else {
            mViewHolder.confirm_img.setImageResource(R.drawable.objection_not_choose);//复用机制让这个语句不起作用
            System.out.println("not_choose");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }//暂时return 5

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_img:
                choosePosition = (int) view.getTag();//记录被点击的item的位置
                firstVisibleItemPositon = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                clickViewHolder = (ViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt((int) view.getTag() - firstVisibleItemPositon));
                if ((chooseViewHolder != clickViewHolder) && (chooseViewHolder != null)) {
                    //逻辑添加
                    clickViewHolder.confirm_img.setImageResource(R.drawable.objection_choose);
                    chooseViewHolder.confirm_img.setImageResource(R.drawable.objection_not_choose);//将上次选中的item设置为未选中
                    chooseViewHolder = clickViewHolder;//记录被选中的viewholder
                    System.out.println("1");
                } else if (chooseViewHolder == clickViewHolder) {//被重复选中的状态
                    //逻辑添加
                    clickViewHolder.confirm_img.setImageResource(R.drawable.objection_not_choose);
                    chooseViewHolder = null;//没有任何控件选中的状态
                    System.out.println("2");
                    choosePosition = -1;
                } else if (chooseViewHolder == null) {
                    clickViewHolder.confirm_img.setImageResource(R.drawable.objection_choose);//没有任何item被选中
                    chooseViewHolder = clickViewHolder;
                    System.out.println("3");
                }
                break;
            case R.id.objection_content_tv:
                firstVisibleItemPositon = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                clickViewHolder = (ViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt((int) view.getTag() - firstVisibleItemPositon));
                if (clickViewHolder.contentTvState == SHRING_STATE) {
                    clickViewHolder.content_tv.setMaxLines(7);
                    clickViewHolder.contentTvState=SPREAD_STATE;
                } else {
                    clickViewHolder.content_tv.setMaxLines(2);
                    clickViewHolder.contentTvState=SHRING_STATE;
                }
                break;
        }
    }

    public int getChoosePosition() {
        return choosePosition;
    }

}
