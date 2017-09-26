package com.baibian.fragment.messy_fiction;

import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.bean.MessageAndNotificationMessage.MessageItemItemItem;
import com.baibian.bean.MessageAndNotificationMessage.MessageSolidItem;
import com.baibian.bean.MessageAndNotificationMessage.MultipleTypes;
import com.baibian.fragment.messy_fiction.MessageFragment.OnListFragmentInteractionListener;
import com.baibian.tool.MeasureTools;
import com.baibian.tool.RecyclerViewCommonTool.CommonLastViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MultipleTypes} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MultipleTypes> mData;
    private final OnListFragmentInteractionListener mListener;
    private boolean isFirstLoad = false;

    private int currentHeight = 0;
    private int titleHeight = 0;
    private boolean isMereContentCollapsed = false;
    public MyMessageRecyclerViewAdapter(List<MultipleTypes> items, OnListFragmentInteractionListener listener) {
        mData = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_detail, parent, false);
                holder = new MessageDetailViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_general, parent, false);
                holder = new MessageGeneralViewHolder(view);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_mere, parent, false);
                holder = new MessageMereViewHolder(view);
                break;
            case -10:
                holder = CommonLastViewHolder.newInstance(parent.getContext());
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        /*if (viewHolder instanceof MessageDetailViewHolder){
            MessageDetailViewHolder mdHolder = (MessageDetailViewHolder) viewHolder;
            mdHolder.messageClickToSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastTools.ToastShow("HelloMessageDetail");
                }
            });

        }*/
        if (viewHolder instanceof MessageMereViewHolder){
            final MessageMereViewHolder mmHolder = (MessageMereViewHolder) viewHolder;
            final LinearLayout rootLinearLayout = mmHolder.mRootLayout;
            LinearLayout contentLayout = (LinearLayout) mmHolder.mRootLayout.findViewById(R.id.message_solid_content);
            final TextView collapseTextView = (TextView) rootLinearLayout.findViewById(R.id.message_solid_collapse_btn);
            final TextView titleTextView = (TextView) rootLinearLayout.findViewById(R.id.message_solid_title);
            List<MessageItemItemItem> innerData = ((MessageSolidItem) mData.get(position)).getmData();
            //以后加载数据的方式，isFirstLoad 用于判断是否是第一次加载，用于防止多次加载时重复添加视图
            /*for (MessageItemItemItem item : innerData && !isFirstLoad){
                View item1 = LayoutInflater.from(contentLayout.getContext()).inflate(R.layout.message_solid_item_item_layout, null);
                TextView userName = (TextView) item1.findViewById(R.id.messy_fiction_head_user_name);
                userName.setText(item.toString());
                contentLayout.addView(item1);
            }*/
            //暂时作为模拟数据源
            for (int i = 0; i < 10 && !isFirstLoad; i++) {
                View item2 = LayoutInflater.from(contentLayout.getContext()).inflate(R.layout.message_solid_item_item_layout, contentLayout);
            }
            isFirstLoad = true;
            //
            currentHeight = MeasureTools.measureWidthAndHeight(rootLinearLayout)[1];
            //点击大标题展开，点击下方小文本收缩
            titleHeight = MeasureTools.measureWidthAndHeight(titleTextView)[1];
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMereContentCollapsed) {
                        expandContent();
                    }
                }
                /**
                 * 展开消息界面
                 */
                private void expandContent() {
                    ValueAnimator animator = ValueAnimator.ofInt(titleHeight, currentHeight);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rootLinearLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
                            rootLinearLayout.requestLayout();
                        }
                    });
                    animator.start();
                    animator.setDuration(1000);
                    collapseTextView.setText("收起");
                    isMereContentCollapsed = false;
                }
            });
            collapseTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collapseContent();
                }

                /**
                 * 收起消息界面
                 */
                private void collapseContent() {
                    ValueAnimator animator = ValueAnimator.ofInt(currentHeight, titleHeight);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            rootLinearLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
                            rootLinearLayout.requestLayout();
                        }
                    });
                    animator.start();
                    animator.setDuration(1000);
                    collapseTextView.setText("展开");
                    isMereContentCollapsed = true;
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        return mData.get(position).getType();
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MessageDetailViewHolder extends RecyclerView.ViewHolder{

        public TextView headUserName;
        public TextView headResponseType;
        public CircleImageView headUserPortrait;
        public TextView messageDate;
        public TextView messageRespondedText;
        public TextView messageRespondedRespondedText;
        public TextView messageClickToSee;

        public MessageDetailViewHolder(View itemView) {
            super(itemView);

            headUserName = (TextView) itemView.findViewById(R.id.messy_fiction_head_user_name);
            headUserPortrait = (CircleImageView) itemView.findViewById(R.id.messy_fiction_head_user_portrait);
            headResponseType = (TextView) itemView.findViewById(R.id.messy_fiction_head_response_type);
            messageDate = (TextView) itemView.findViewById(R.id.message_item_date);
            messageClickToSee = (TextView) itemView.findViewById(R.id.message_item_click_to_see);
            messageRespondedRespondedText = (TextView) itemView.findViewById(R.id.message_item_responded_responded_text);
            messageRespondedText = (TextView) itemView.findViewById(R.id.message_item_respond);

        }
    }
    public class MessageGeneralViewHolder extends RecyclerView.ViewHolder {
/*
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
*/
        public RelativeLayout mFirstContentView;
        public TextView mFirstContentTitle;
        public TextView mFirstContentBegan;
        public TextView mFirstContentNewEmerged;

        public RelativeLayout mSecondContentView;
        public TextView mSecondContentUserUser;
        public TextView mSecondContentUserUserName;
        public TextView mSecondContentUserAttend;
        public TextView mSecondContentUserAttendContent;
        public TextView mSecondContentUserUser2;
        public TextView mSecondContentUserFocused2;

        public TextView mClickToSee;

//        public MultipleTypes mItem;

        public MessageGeneralViewHolder(View view) {
            super(view);
            
            mFirstContentView = (RelativeLayout) view.findViewById(R.id.content_message);
            mFirstContentBegan = (TextView) view.findViewById(R.id.content_began);
            mFirstContentNewEmerged = (TextView) view.findViewById(R.id.content_new_emerged);
            mFirstContentTitle = (TextView) view.findViewById(R.id.content_title);

            mSecondContentView = (RelativeLayout) view.findViewById(R.id.user_message);
            mSecondContentUserAttendContent = (TextView) view.findViewById(R.id.user_attend_content);
            mSecondContentUserAttend = (TextView) view.findViewById(R.id.user_attend);
            mSecondContentUserFocused2 = (TextView) view.findViewById(R.id.user_focused_2);
            mSecondContentUserUser = (TextView) view.findViewById(R.id.user_user);
            mSecondContentUserUser2 = (TextView) view.findViewById(R.id.user_user_2);
            mSecondContentUserUserName = (TextView) view.findViewById(R.id.user_user_name);

            mClickToSee = (TextView) view.findViewById(R.id.click_to_see);
/*
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.user);
            mContentView = (TextView) view.findViewById(R.id.content);
*/
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSecondContentUserUserName.getText() + "'";
        }
    }
    public class MessageMereViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mRootLayout;
        public MessageMereViewHolder(View itemView) {
            super(itemView);
//            mRootLayout = (LinearLayout) itemView.findViewById(R.id.message_solid_root);
            mRootLayout = (LinearLayout) itemView;
        }
    }
}
