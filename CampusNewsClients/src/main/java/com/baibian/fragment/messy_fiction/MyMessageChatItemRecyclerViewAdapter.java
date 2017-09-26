package com.baibian.fragment.messy_fiction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.bean.MessageAndNotificationMessage.MessageChatItemContent;
import com.baibian.fragment.messy_fiction.ChatFragment.OnListFragmentInteractionListener;
import com.baibian.fragment.messy_fiction.dummy.DummyContent.DummyItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessageChatItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageChatItemRecyclerViewAdapter.ViewHolder> {

    private final List<MessageChatItemContent.MessageChatItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMessageChatItemRecyclerViewAdapter(List<MessageChatItemContent.MessageChatItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messagechatitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
//        依如下形式填充数据
//        Glide.with(...).load(...).
//        holder.mUserName.setText(holder.mItem.);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MessageChatItemContent.MessageChatItem mItem;
        public TextView mUserName;
        public TextView mUserSignature;
        public TextView mUserDate;
        public CircleImageView mUserPortrait;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserPortrait = (CircleImageView) mView.findViewById(R.id.user_portrait);
            mUserName = (TextView) mView.findViewById(R.id.user_name);
            mUserDate = (TextView) mView.findViewById(R.id.user_date);
            mUserSignature = (TextView) mView.findViewById(R.id.user_signature);
        }
/*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
