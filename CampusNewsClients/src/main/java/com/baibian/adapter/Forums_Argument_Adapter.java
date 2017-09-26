package com.baibian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.information.ForumsArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ????? on 2017/7/25.
 */

/**
 * ???????adapter
 */
public class Forums_Argument_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder mViewHolder;
    private List<ForumsArgument> mforumsArgumentList = new ArrayList<>();//存储论点的类的数组
    private static final int LASTITEM = 0;
    private static final int NORMALITEM = 1;
    private NoMoreViewHolder noMoreViewHolder;
    private ForumsOnItemClickListener forumsOnItemClickListener;
    private ForumsArgument forumsArgument;
    private int firstVisibleItemPosition;
    private RecyclerView mRecyclerView;
    private ViewHolder clickViewHolder;
    private Window mWindow;
    private int dislikeTvClickPositon = -1;//记录弹出框所在的item的位置//TODO 权宜之计

    public interface ForumsOnItemClickListener {
        void onItemClick();//这里可以考虑传递一些参数过去
    }

    public void setForumsOnItemClickListener(ForumsOnItemClickListener forumsOnItemClickListener) {
        this.forumsOnItemClickListener = forumsOnItemClickListener;
    }

    public Forums_Argument_Adapter(Context context, List<ForumsArgument> forumsargumentList, RecyclerView recyclerView, Window mWindow) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mforumsArgumentList = forumsargumentList;
        this.mRecyclerView = recyclerView;
        this.mWindow = mWindow;
        forumsArgument = new ForumsArgument();
        forumsArgument.setArgument("题目", "  权利天赋即自然权利（Natural Right），最早的表述或来自古希腊斯多噶学派，强调人类在自然状态下固有的，神圣不可侵犯和剥夺的权利。因此，人类的权利来自天然，所以一切...", 0);
        mforumsArgumentList.add(forumsArgument);
    }

    /**
     * *viewholder数据缓存器
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;//????
        private TextView content;//???????
        private TextView perfectNum;//???????
        private TextView concentrate;//??????
        private TextView dislike_tv;
        private boolean concentrateState = false;
        private boolean repeatTagState = false;
        private boolean badTagState = false;
        private boolean unfriendlyTagState = false;
        private boolean otherTagState = false;
        private boolean reportTagState = false;
        private View itemView;
        private TextView finish_tv;
        private TextView repeat_tag_tv;
        private TextView bad_tag_tv;
        private TextView unfriendly_tag_tv;
        private TextView other_tag_tv;
        private LinearLayout report_layout;
        private PopupWindow dislike_popupWindow;
        private View popContentView;
        private int[] tagState = {0, 0, 0, 0};//记录tag状态的数组,按次序分别为，重复，低质量，不友善，其它,0代表为点击，1代表已点击


        public ViewHolder(View view) {
            super(view);
            itemView = view;
            title = (TextView) view.findViewById(R.id.forums_argument_title_tv);
            content = (TextView) view.findViewById(R.id.forums_argument_content_tv);
            perfectNum = (TextView) view.findViewById(R.id.forums_perfect_num_tv);
            concentrate = (TextView) view.findViewById(R.id.forums_concentrate_tv);
            dislike_tv = (TextView) view.findViewById(R.id.dislike_tv);
            initPopupWindow();
            /**
             * 这里标签监听的位置有待考虑
             */
            repeat_tag_tv.setOnClickListener(this);
            bad_tag_tv.setOnClickListener(this);
            unfriendly_tag_tv.setOnClickListener(this);
            other_tag_tv.setOnClickListener(this);
            report_layout.setOnClickListener(this);
            dislike_tv.setOnClickListener(this);
            itemView.setOnClickListener(this);
            concentrate.setOnClickListener(this);
            finish_tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /**
                 * 利用dislike_tv,popupwindow弹出的时间上的连续性，获取一个同一的viewHOlder，共用
                 */
                case R.id.repeat_tag_tv:
                    if (!clickViewHolder.repeatTagState) {
                        clickViewHolder.repeat_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.repeatTagState = true;
                        clickViewHolder.tagState[0] = 1;
                    } else {
                        clickViewHolder.repeat_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.repeatTagState = false;
                        clickViewHolder.tagState[0] = 0;
                    }
                    break;
                case R.id.bad_tag_tv:
                    if (!clickViewHolder.badTagState) {
                        clickViewHolder.bad_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.badTagState = true;
                        clickViewHolder.tagState[1] = 1;
                    } else {
                        clickViewHolder.bad_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.badTagState = false;
                        clickViewHolder.tagState[1] = 0;
                    }
                    break;
                case R.id.unfriendly_tag_tv:
                    if (!clickViewHolder.unfriendlyTagState) {
                        clickViewHolder.unfriendly_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.unfriendly_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.unfriendlyTagState = true;
                        clickViewHolder.tagState[2] = 1;
                    } else {
                        clickViewHolder.unfriendly_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.unfriendly_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.unfriendlyTagState = false;
                        clickViewHolder.tagState[2] = 0;
                    }
                    break;
                case R.id.other_tag_tv:
                    if (!clickViewHolder.otherTagState) {
                        clickViewHolder.other_tag_tv.setBackgroundResource(R.drawable.red_fill_rect);
                        clickViewHolder.other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                        clickViewHolder.otherTagState = true;
                        clickViewHolder.tagState[3] = 1;
                    } else {
                        clickViewHolder.other_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
                        clickViewHolder.other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
                        clickViewHolder.otherTagState = false;
                        clickViewHolder.tagState[3] = 0;
                    }
                    break;
                case R.id.finish_tv:
                    /**
                     * 删除前必须选一个理由才能删除
                     */
                    for (int state : clickViewHolder.tagState) {
                        if (state == 1) {
                            /**
                             * 如果有选中的代表可以删除
                             */
                            mforumsArgumentList.remove(dislikeTvClickPositon);//TODO 抓住dislike_tv时间上的连续性，巧用dislike_click_position
                            notifyDataSetChanged();
                            break;
                        }
                    }
                    dislike_popupWindow.dismiss();//隐藏
                    /**
                     * 添加向后台提交数据的逻辑
                     */
                    break;
                case R.id.report_layout:
                    /**
                     * 举报逻辑
                     */
                    break;
                case R.id.dislike_tv:
                    dislikeTvClickPositon = (int) v.getTag();
                    clickViewHolder = getClickViewHolder(dislikeTvClickPositon);
                    dislike_popupWindow.showAsDropDown(dislike_tv);
                    makeScreenDark();
                    break;
                case R.id.forums_concentrate_tv:
                    clickViewHolder = getClickViewHolder((int) v.getTag());
                    if (!concentrateState) {
                        clickViewHolder.concentrate.setText(R.string.have_concentrate);//这样获取的viewHolde是可见的最后一个item的viewHolder
                        concentrateState = true;
                    } else {
                        clickViewHolder.concentrate.setText(R.string.concentrate);
                        concentrateState = false;
                    }
                    break;
                case R.id.forums_argument_item_layout:
                    if (forumsOnItemClickListener != null) {
                        forumsOnItemClickListener.onItemClick();//TODO 这里还要插入position等信息，现在还不需要，先不考虑
                    }

            }

        }

        private void initTagsState() {
            repeatTagState = false;
            badTagState = false;
            unfriendlyTagState = false;
            otherTagState = false;
            reportTagState = false;
            repeat_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            repeat_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            bad_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            bad_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            unfriendly_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            unfriendly_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            unfriendly_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));
            other_tag_tv.setBackgroundResource(R.drawable.gray_light_rect);
            other_tag_tv.setTextColor(mContext.getResources().getColor(R.color.middle_gray));


        }

        private void initPopupWindow() {
            popContentView = LayoutInflater.from(mContext).inflate(R.layout.dislike_popupwindow2, null);
            dislike_popupWindow = new PopupWindow(popContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            dislike_popupWindow.setOutsideTouchable(true);
            repeat_tag_tv = (TextView) popContentView.findViewById(R.id.repeat_tag_tv);
            bad_tag_tv = (TextView) popContentView.findViewById(R.id.bad_tag_tv);
            unfriendly_tag_tv = (TextView) popContentView.findViewById(R.id.unfriendly_tag_tv);
            other_tag_tv = (TextView) popContentView.findViewById(R.id.other_tag_tv);
            report_layout = (LinearLayout) popContentView.findViewById(R.id.report_layout);
            finish_tv = (TextView) popContentView.findViewById(R.id.finish_tv);
            dislike_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    makeScreenLight();
                }
            });
        }

        private ViewHolder getClickViewHolder(int currentPosition) {
            firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            return (ViewHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(currentPosition - firstVisibleItemPosition));

        }
    }

    class NoMoreViewHolder extends RecyclerView.ViewHolder {
        private NoMoreViewHolder(View view) {
            super(view);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMALITEM) {
            view = mInflater.inflate(R.layout.forums_argument_recycler_item, parent, false);
            return mViewHolder = new ViewHolder(view);
        } else if (viewType == LASTITEM) {
            /**
             * 在这里添加没有更多内容的选择逻辑
             */
            //view = mInflater.inflate(R.layout.forums_argument_recycler_nomore_item, parent, false);
            view = mInflater.inflate(R.layout.forums_argument_recycler_loading_item, parent, false);
            return noMoreViewHolder = new NoMoreViewHolder(view);
        }
        return null;
    }

    /*
    **?????????
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            mViewHolder = (ViewHolder) holder;
            mViewHolder.title.setText(mforumsArgumentList.get(position).getTitle());
            mViewHolder.content.setText(mforumsArgumentList.get(position).getContent());
            String test = mforumsArgumentList.get(position).getPerfectNum() + "";
            mViewHolder.perfectNum.setText(test);
            mViewHolder.itemView.setTag(position);
            mViewHolder.concentrate.setTag(position);
            mViewHolder.dislike_tv.setTag(position);

        } else if (holder instanceof NoMoreViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return LASTITEM;//最后一个item
        } else return NORMALITEM;
    }

    @Override
    public int getItemCount() {
        return mforumsArgumentList.size() + 1;
    }//这里应该返回后台传入的数据+1，最后一个位置留给“没有更多内容的提示”


    public void addItem(List<ForumsArgument> newForumsArgumentList) {
        /**
         * 实现新数据在前，旧数据在后
         */
        newForumsArgumentList.addAll(mforumsArgumentList);
        mforumsArgumentList.removeAll(mforumsArgumentList);
        mforumsArgumentList.addAll(newForumsArgumentList);
        notifyDataSetChanged();

    }

    public void addMoreItem(List<ForumsArgument> newForumsArgumentList) {
        /**
         * 上拉加载时实现新数据在后
         */
        mforumsArgumentList.addAll(newForumsArgumentList);
        notifyDataSetChanged();
    }


    /**
     * popupWindow弹出时屏幕变暗
     */

    private void makeScreenDark() {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 0.7f;
        mWindow.setAttributes(lp);
    }

    /**
     * popupWindow隐藏时屏幕恢复原亮度
     */
    private void makeScreenLight() {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 1f;
        mWindow.setAttributes(lp);
    }

}
