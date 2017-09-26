package com.baibian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baibian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * �ܹ�ʵ������ˢ�£��»����ظ����adapter,,��Ϊһ�������������
 */
public class RefreshFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //�������ظ���
    public static final int  PULLUP_LOAD_MORE=0;
    //���ڼ�����
    public static final int  LOADING_MORE=1;
    //�������ظ���״̬-Ĭ��Ϊ0

    public static final int FIRST_ITEM=2;//��һ��������ĵ�����

    private int load_more_status=0;
    private LayoutInflater mInflater;
    private List<String> mTitles=null;
    private static final int TYPE_ITEM = 0;  //��ͨItem View
    private static final int TYPE_FOOTER = 1;  //����FootView
    public RefreshFootAdapter(Context context){
        this.mInflater= LayoutInflater.from(context);
        this.mTitles=new ArrayList<String>();
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles.add("item"+index);
        }
    }
    /**
     * item��ʾ����
     * @param parent
     * @param viewType
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //�����ж���ʾ���ͣ����������ز�ͬ��View
        if(viewType==TYPE_ITEM){
            View view=mInflater.inflate(R.layout.item_recycler_layout,parent,false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder=new ItemViewHolder(view);
            return itemViewHolder;
        }else if(viewType==TYPE_FOOTER){
            View foot_view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder=new FootViewHolder(foot_view);
            return footViewHolder;
        }else if (viewType==FIRST_ITEM){
            //��һ��������ĳ�Ϊviewpager
          //  View view=mInflater.inflate(R.layout.testlayout,parent,false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
       //     FirstViewHolder firstViewHolder=new FirstViewHolder(view);
         //   return firstViewHolder;
        }
        return null;
    }

    /**
     * ���ݵİ���ʾ
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ((ItemViewHolder)holder).item_tv.setText(mTitles.get(position));
            holder.itemView.setTag(position);
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText(R.string.load_more);
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText(R.string.loading_more);
                    break;
            }
        }
    }

    /**
     * �����ж�����ͨItem��ͼ����FootView��ͼ
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position==0){
            //��ʱ��һ������ʱ������ʹ����
           return FIRST_ITEM;
        }
        // ���һ��item����ΪfooterView
        else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    @Override
    public int getItemCount() {
        return mTitles.size()+1;
    }
    //�Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ItemViewHolder(View view){
            super(view);
            item_tv = (TextView)view.findViewById(R.id.item_tv);
        }
    }

    /**
     * �����Ĳ���
     */
    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public FirstViewHolder(View view){
            super(view);
            //item_tv = (TextView)view.findViewById(R.id.item_tv);
        }
    }
    /**
     * �ײ�FootView����
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private TextView foot_view_item_tv;
        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view_item_tv);
        }
    }

    //�������
    public void addItem(List<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mTitles);
        mTitles.removeAll(mTitles);
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<String> newDatas) {
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    /**
     * //�������ظ���
     * PULLUP_LOAD_MORE=0;
     * //���ڼ�����
     * LOADING_MORE=1;
     * //��������Ѿ�û�и���������
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }

}