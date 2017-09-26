package com.baibian.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.activity.ScanArgumentActivity;
import com.baibian.information.Comments;

import java.util.List;

/**
 * Created by Light on 2017/8/14.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comments> mCommentsList;
    private Context mContext;
    public OnItemClickListener onItemClickListener;
    public CommentAdapter(List<Comments> commentList,Context context){
        mCommentsList = commentList;
        this.mContext=context;
    }
    public interface OnItemClickListener{
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onItemClickListener.onItemClick();//利用回调
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comments comments = mCommentsList.get(position);
        // TODO: 2017/8/24  设置这个日期等

        holder.Date.setText(comments.getDate());
        holder.Content.setText(comments.getContent());
        holder.Title.setText(comments.getTitle());
        holder.headImage.setImageResource(comments.getImageId());
        holder.userName.setText(comments.getUserName());
        holder.isSolved = comments.getIsSolved();

        if (holder.isSolved) {
            holder.tv_isSolved.setText("已解决");
        } else {
            holder.tv_isSolved.setText("未解决");
        }

    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headImage;
        TextView userName;
        boolean isSolved;
        TextView tv_isSolved;
        TextView Title;
        TextView Content;
        TextView Date;



        public ViewHolder(View itemView) {
            super(itemView);
            headImage = (ImageView) itemView.findViewById(R.id.head_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            Title = (TextView) itemView.findViewById(R.id.comment_title);
            Content = (TextView) itemView.findViewById(R.id.comment_item);
            Date = (TextView) itemView.findViewById(R.id.comment_date);
            tv_isSolved = (TextView) itemView.findViewById(R.id.tv_isSolved);
// TODO: 2017/8/24  放入是否解决
            isSolved = true;
        }
    }



}

