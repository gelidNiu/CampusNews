package com.baibian.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.tool.HttpTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.view.ViewGroup.LayoutParams;


/**
 * �ܹ�ʵ������ˢ�£��»����ظ����adapter
 */
public class Forums_Integration_Refresh_FootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyItemClickListener mItemClickListener;
    /**
     * progresstext���������������������Ķ���,,������ʱʹ�ó�ʼ��ֵ��Ϊ����
     */
    private float positiveNumber = 0;//��������
    private float neutralNumber = 0;//��������
    private float negetiveNumber = 0;//��������

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

    //   public static final int FIRST_ITEM = 2;//��һ��������ĵ�����

    private int load_more_status = 0;
    private LayoutInflater mInflater;
    private List<String> mTitles = null;
    private static final int TYPE_ITEM = 0;  //��ͨItem View
    private static final int TYPE_FOOTER = 1;  //����FootView
    private Window mWindow;
    private ItemViewHolder clickViewHolder;
    private int firstVisibleItemPosition;
    private RecyclerView mRecyclerView;
    private int dislikeTvPosition;

    public Forums_Integration_Refresh_FootAdapter(Context context,Window mWindow,RecyclerView mRecyclerView) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mTitles = new ArrayList<String>();
        this.mWindow=mWindow;
        this.mRecyclerView=mRecyclerView;
        for (int i = 0; i < 2; i++) {
            int index = i + 1;
            mTitles.add(context.getString(R.string.text_topic));
        }
    }

    /**
     * item��ʾ����
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //�����ж���ʾ���ͣ����������ز�ͬ��View
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.forums_recycler_item_layout, parent, false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view, mItemClickListener);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            //��߿�����һЩ�������ã������¼�������
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
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
            ((ItemViewHolder) holder).item_tv.setText(mTitles.get(position));//������������Ŀ����
            holder.itemView.setTag(position);//��ÿ��item����һ����ǩ�����Էֱ�ÿ��item
            /**
             * progresstext���㷨
             */
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();
            String positiveString = "" + (int) positiveNumber;
            String negetiveString = "" + (int) negetiveNumber;
            String neutralString = "" + (int) neutralNumber;
            float progress_text1width = (width * positiveNumber / (positiveNumber + negetiveNumber + neutralNumber));
            float progress_text2width = (width * neutralNumber / (positiveNumber + negetiveNumber + neutralNumber));
            float progress_text3width = width * negetiveNumber / (positiveNumber + negetiveNumber + neutralNumber);
            ((ItemViewHolder) holder).progress_relativelayout3.setMinimumWidth((int) progress_text3width);
            ((ItemViewHolder) holder).progress_relativelayout2.setMinimumWidth((int) progress_text2width);
            ((ItemViewHolder) holder).progress_relativelayout1.setMinimumWidth((int) progress_text1width);
            ((ItemViewHolder) holder).progress_text3.setText(negetiveString);
            ((ItemViewHolder) holder).progress_text2.setText(neutralString);
            ((ItemViewHolder) holder).progress_text1.setText(positiveString);
            ((ItemViewHolder)holder).dislike_tv.setTag(position);
            ((ItemViewHolder)holder).concentrate_tv.setTag(position);
            ((ItemViewHolder)holder).praise_img.setTag(position);

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
        // ���һ��item����ΪfooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    //�Զ����ViewHolder������ÿ��Item�ĵ����н���Ԫ��
    public  class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public float itemwidth;
        private LinearLayout forums_item_all_layout;
        public TextView item_tv;
        private TextView progress_text1;
        private TextView progress_text2;
        private TextView progress_text3;
        private RelativeLayout progress_relativelayout1;
        private RelativeLayout progress_relativelayout2;
        private RelativeLayout progress_relativelayout3;
        private MyItemClickListener mListener;
        private TextView dislike_tv;
        private boolean repeatTagState=false;//̽�ֱ���״̬�ĸ�Ч����
        private boolean badTagState=false;
        private boolean tag1State=false;
        private boolean tag2State=false;
        private boolean otherTagState=false;
        private View contentView;
        private PopupWindow dislike_popupWindow;
        /**
         * �����ǩ
         */
        private TextView repeat_tag_tv;
        private TextView finish_tv;
        private TextView bad_tag_tv;
        private TextView tag1_tv;
        private TextView tag2_tv;
        private TextView other_tag_tv;
        private LinearLayout report_layout;//�ٱ���ť
        private int[] tagState = {0,0,0,0,0};
        private TextView concentrate_tv;//��ע���ⰴť
        private boolean concentrateState=false;//��ע״̬
        private ImageView praise_img;
        private boolean praiseState=false;



        //   private ProgressBar forums_item_progressBar;
        public ItemViewHolder(View view) {
            super(view);
            item_tv = (TextView) view.findViewById(R.id.item_tv);
            //  forums_item_progressBar=(ProgressBar) view.findViewById(R.id.forums_item_progressBar);
        }

        public ItemViewHolder(View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.item_tv);
            forums_item_all_layout = (LinearLayout) itemView.findViewById(R.id.forums_item_all_layout);
            progress_text1 = (TextView) itemView.findViewById(R.id.progress_text1);
            progress_text2 = (TextView) itemView.findViewById(R.id.progress_text2);
            progress_text3 = (TextView) itemView.findViewById(R.id.progress_text3);
            progress_relativelayout1 = (RelativeLayout) itemView.findViewById(R.id.progress_relativelayout1);
            progress_relativelayout2 = (RelativeLayout) itemView.findViewById(R.id.progress_relativelayout2);
            progress_relativelayout3 = (RelativeLayout) itemView.findViewById(R.id.progress_relativelayout3);
            concentrate_tv=(TextView)itemView.findViewById(R.id.forums_concentrate_tv);
            praise_img=(ImageView)itemView.findViewById(R.id.forums_praise_img);
            dislike_tv = (TextView) itemView.findViewById(R.id.integration_dislike_tv);
            dislike_tv.setOnClickListener(this);
            initPopupWindow();
            repeat_tag_tv.setOnClickListener(this);
            bad_tag_tv.setOnClickListener(this);
            tag1_tv.setOnClickListener(this);
            tag2_tv.setOnClickListener(this);
            other_tag_tv.setOnClickListener(this);
            finish_tv.setOnClickListener(this);
            report_layout.setOnClickListener(this);
            concentrate_tv.setOnClickListener(this);
            praise_img.setOnClickListener(this);
            //dislike_tv.setOnClickListener(this);
            //   forums_item_progressBar=(ProgressBar) itemView.findViewById(R.id.forums_item_progressBar);
            this.mListener = myItemClickListener;//��activityͨ��
            itemView.setOnClickListener(this);
        }

        /**
         * ��Ϊ�����õĶ���ͬһ��popupWindow��������ڴ�Ч�ʣ������ݿ����ص�������ÿ�ζ�Ҫ��������״̬
         */
        private void initTagsState()
        {
            repeatTagState=false;
            badTagState=false;
            tag1State=false;
            tag2State=false;
            otherTagState=false;
            repeat_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            bad_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            tag1_tv.setBackgroundResource(R.drawable.gray_light_rect);
            tag1_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            tag2_tv.setBackgroundResource(R.drawable.gray_light_rect);
            tag2_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            other_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
        }
        private void initPopupWindow()
        {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.dislike_popupwindow1, null);
            dislike_popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
            dislike_popupWindow.setOutsideTouchable(true);
            repeat_tag_tv=(TextView)contentView.findViewById(R.id.repeat_tag_tv);
            bad_tag_tv=(TextView)contentView.findViewById(R.id.bad_tag_tv);
            tag1_tv=(TextView)contentView.findViewById(R.id.tag1_tv);
            tag2_tv=(TextView)contentView.findViewById(R.id.tag2_tv);
            other_tag_tv=(TextView)contentView.findViewById(R.id.other_tag_tv);
            finish_tv=(TextView)contentView.findViewById(R.id.finish_tv);
            report_layout=(LinearLayout)contentView.findViewById(R.id.report_layout);
            dislike_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    makeScreenLight();
                }
            });
        }
        private ItemViewHolder getClickViewHolder(int currentPosition) {
            firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            return (ItemViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(currentPosition - firstVisibleItemPosition));

        }
        /**
         * ʵ��OnClickListener�ӿ���д�ķ���
         *
         * @param v
         */
        @Override
        public void onClick(View v) {//һ����item������������δ���
            switch (v.getId())
            {
                case R.id.repeat_tag_tv:
                    if(!clickViewHolder.repeatTagState){
                        clickViewHolder.repeat_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.repeatTagState=true;
                        tagState[0]=1;
                    }else{
                        clickViewHolder.repeat_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.repeatTagState=false;
                        tagState[0]=0;
                    }
                    break;
                case R.id.bad_tag_tv:
                    if(!clickViewHolder.badTagState){
                        clickViewHolder.bad_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.badTagState=true;
                        tagState[1]=1;
                    }else{
                        clickViewHolder.bad_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.badTagState=false;
                        tagState[1]=0;
                    }
                    break;
                case R.id.tag1_tv:
                    if (!clickViewHolder.tag1State) {
                        clickViewHolder.tag1_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.tag1_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.tag1State=true;
                        tagState[2]=1;
                    }else{
                        clickViewHolder.tag1_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.tag1_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.tag1State=false;
                        tagState[2]=0;
                    }
                    break;
                case R.id.tag2_tv:
                    if(!clickViewHolder.tag2State){
                        clickViewHolder.tag2_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.tag2_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.tag2State=true;
                        tagState[3]=1;
                    }else{
                        clickViewHolder.tag2_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.tag2_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.tag2State=false;
                        tagState[3]=0;
                    }
                    break;
                case R.id.other_tag_tv:
                    if(!clickViewHolder.otherTagState){
                        clickViewHolder.other_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.otherTagState=true;
                        tagState[4]=1;
                    }else{
                        clickViewHolder.other_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.otherTagState=false;
                        tagState[4]=0;
                    }
                    break;
                case R.id.finish_tv:
                    dislike_popupWindow.dismiss();//����
                    /**
                     * ������̨�ύ���ݵ��߼�
                     */
                    /**
                     * ɾ��ǰ����ѡһ�����ɲ���ɾ��
                     */
                    for (int state : clickViewHolder.tagState) {
                        if (state == 1) {
                            /**
                             * �����ѡ�еĴ������ɾ��
                             */
                            mTitles.remove(dislikeTvPosition);//TODO ץסdislike_tvʱ���ϵ������ԣ�����dislike_click_position
                            notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case R.id.report_layout:
                    /**
                     * �ٱ��߼�
                     */
                    break;
                case R.id.integration_dislike_tv:
                    dislikeTvPosition=(int)v.getTag();
                    clickViewHolder=getClickViewHolder((int)v.getTag());
                    clickViewHolder.dislike_popupWindow.showAsDropDown(dislike_tv);
                    makeScreenDark();
                    break;
                case R.id.forums_item_all_layout:
                    if(mListener!=null){
                        mListener.onItemClick(v,getPosition());
                    }
                    break;
                case R.id.forums_concentrate_tv:
                    clickViewHolder=getClickViewHolder((int)v.getTag());
                    if(!clickViewHolder.concentrateState){
                        clickViewHolder.concentrate_tv.setText(R.string.have_concentrate);
                        clickViewHolder.concentrateState=true;
                    }else {
                        clickViewHolder.concentrate_tv.setText(R.string.concentrate_debate);
                        clickViewHolder.concentrateState=false;
                    }
                    break;
                case R.id.forums_praise_img:
                    clickViewHolder=getClickViewHolder((int)v.getTag());
                    if(!clickViewHolder.praiseState){
                        clickViewHolder.praiseState=true;
                    }else{
                        clickViewHolder.praiseState=false;
                    }
                    break;

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
         *
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
    /**
     * popupWindow����ʱ��Ļ�䰵
     */

    private void makeScreenDark() {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 0.7f;
        mWindow.setAttributes(lp);
    }

    /**
     * popupWindow����ʱ��Ļ�ָ�ԭ����
     */
    private void makeScreenLight() {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 1f;
        mWindow.setAttributes(lp);
    }
}



