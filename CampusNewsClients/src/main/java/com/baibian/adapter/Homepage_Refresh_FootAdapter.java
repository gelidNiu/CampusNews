package com.baibian.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baibian.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * �ܹ�ʵ������ˢ�£��»����ظ����adapter,��ʵ����recyclerview��adapter
 */
public class Homepage_Refresh_FootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyItemClickListener mItemClickListener;
    /**
     *
     */
    private Context mContext;
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler;
    private View mViewPage;
    private ViewPager adsViewPager;
    private int currentItem = 0;
    private List<ImageView> imageViews;
    private int[] imageResId;
    private List<View> dots;
    private static int oldPosition;

    //�������ظ���
    public static final int PULLUP_LOAD_MORE = 0;
    //���ڼ�����
    public static final int LOADING_MORE = 1;
    //�������ظ���״̬-Ĭ��Ϊ0

    public static final int FIRST_ITEM = 2;//��һ��������ĵ�����

    private int load_more_status = 0;
    private LayoutInflater mInflater;
    private List<String> mTitles = null;
    private static final int TYPE_ITEM = 0;  //��ͨItem View
    private static final int TYPE_FOOTER = 1;  //����FootView

    public Homepage_Refresh_FootAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            int index = i + 1;
            mTitles.add(context.getString(R.string.text_topic));
        }

    }

    /**
     * item��ʾ����
     *
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //�����ж���ʾ���ͣ����������ز�ͬ��View
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_recycler_layout, parent, false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view,mItemClickListener);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        } else if (viewType == FIRST_ITEM) {
            //��һ��������ĳ�Ϊviewpager
            View mViewPage = mInflater.inflate(R.layout.homepage_list_item_viewpager, parent, false);//�������Ҫ��false������ԭ���Ĳ����ڣ�����¼���Ĳ��ֲ�����ͻ
            adsViewPager = (ViewPager) mViewPage.findViewById(R.id.homepage_ViewPager);
            oldPosition = 0;
            initImagesId();
            addImageViews();
            initDots(mViewPage);
            initChangePicHandler();
            startScheduledExecutorService();
            adsViewPager.setAdapter(new Homepage_ViewpagerAdapter(imageViews, imageResId));
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            FirstViewHolder firstViewHolder = new FirstViewHolder(mViewPage);
            return firstViewHolder;
        }
        return null;
    }

    /**
     * ���ݵİ���ʾ
     *
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).item_tv.setText(mTitles.get(position));
            holder.itemView.setTag(position);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
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
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
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
        return mTitles.size()+1 ;
    }

    //�Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item_tv;
        private  MyItemClickListener mListener;
        public ItemViewHolder(View view) {
            super(view);
            item_tv = (TextView) view.findViewById(R.id.item_tv);
        }
        public ItemViewHolder(View itemView,MyItemClickListener myItemClickListener){
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.item_tv);
            this.mListener=myItemClickListener;
            itemView.setOnClickListener(this);
        }
        /**
         * ʵ��OnClickListener�ӿ���д�ķ���
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }

        }
    }

    /**
     * �����Ĳ���
     */
    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;

        public FirstViewHolder(View view) {
            super(view);
            //item_tv = (TextView)view.findViewById(R.id.item_tv);
        }
    }

    /**
     * �ײ�FootView����
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view_item_tv);
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
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    /***
     *
     */

    public void initImagesId() {
        imageResId = new int[]{R.drawable.ads0, R.drawable.ads1,
                R.drawable.ads2, R.drawable.ads3, R.drawable.ads4};
    }

    public void addImageViews() {
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageResId.length; i++) {
            final ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
    }

    public void initDots(View mViewPage) {
        dots = new ArrayList<View>();
        dots.add(mViewPage.findViewById(R.id.v_dot0));
        dots.add(mViewPage.findViewById(R.id.v_dot1));
        dots.add(mViewPage.findViewById(R.id.v_dot2));
        dots.add(mViewPage.findViewById(R.id.v_dot3));
        dots.add(mViewPage.findViewById(R.id.v_dot4));
    }

    @SuppressLint("HandlerLeak")
    public void initChangePicHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                if (msg.what == 1002) {
                    adsViewPager.setCurrentItem(currentItem);
                }
            }

            ;
        };
    }

    public void startScheduledExecutorService() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1002;
                currentItem = (currentItem + 1) % imageViews.size();
                handler.sendMessage(msg);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }
    /**
     *
     */




    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;

        public ViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            //��ȫ�ֵļ�����ֵ���ӿ�
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        /**
         * ʵ��OnClickListener�ӿ���д�ķ���
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }

        }
    }

    /**
     * ����һ���ص��ӿ�
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * ��activity����adapter���ǵ��õ��������,������¼��������ݹ���,����ֵ��ȫ�ֵļ���
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}



