package com.baibian.view.LunBa;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibian.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Ellly on 2017/8/15.
 * 评论卡片的自定义view
 */

public class CommentCardView extends RelativeLayout {

    public static class CardContent{

        private String mPortraitUrl;
        private String mUserName;
        private long mCommentDate;
        private String mCommentContent;
        private long mLikeAmount;

        /**
         * 对应的数据结构
         * @param mPortraitUrl
         * @param mUserName
         * @param mCommentDate
         * @param mCommentContent
         * @param mLikeAmount
         */
        public CardContent(String mPortraitUrl, String mUserName, long mCommentDate, String mCommentContent, long mLikeAmount) {
            this.mPortraitUrl = mPortraitUrl;
            this.mUserName = mUserName;
            this.mCommentDate = mCommentDate;
            this.mCommentContent = mCommentContent;
            this.mLikeAmount = mLikeAmount;
        }
        public CardContent(){}

        public String getmPortraitUrl() {
            return mPortraitUrl;
        }

        public void setmPortraitUrl(String mPortraitUrl) {
            this.mPortraitUrl = mPortraitUrl;
        }

        public String getmUserName() {
            return mUserName;
        }

        public void setmUserName(String mUserName) {
            this.mUserName = mUserName;
        }

        public long getmCommentDate() {
            return mCommentDate;
        }

        public void setmCommentDate(long mCommentDate) {
            this.mCommentDate = mCommentDate;
        }

        public String getmCommentContent() {
            return mCommentContent;
        }

        public void setmCommentContent(String mCommentContent) {
            this.mCommentContent = mCommentContent;
        }

        public long getmLikeAmount() {
            return mLikeAmount;
        }

        public void setmLikeAmount(long mLikeAmount) {
            this.mLikeAmount = mLikeAmount;
        }
    }

    private CardContent mCardContent;
    private CardView mCardRoot;
    private CircleImageView mUserPortrait;
    private TextView mDate;
    private TextView mName;
    private TextView mContent;
    private TextView mAmount;
    private LikeButton mLikeButton;

    public void setContent(CardContent content){
        mCardContent = content;
    }
    public CommentCardView setCommentText(String text){
        mContent.setText(text);
        return this;
    }

    public CommentCardView(Context context) {
        super(context);
        init();
    }

    public CommentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.activity_periodical_detail_comment_card_view, this);
        mCardRoot = (CardView) findViewById(R.id.comment_root_view);
        mCardRoot.setCardElevation(30);
        mUserPortrait = (CircleImageView) findViewById(R.id.user_portrait);
        mContent = (TextView) findViewById(R.id.comment_content);
        mAmount = (TextView) findViewById(R.id.like_amount);
        mName = (TextView) findViewById(R.id.user_name);
        mDate = (TextView) findViewById(R.id.comment_date);
        mLikeButton = (LikeButton) findViewById(R.id.like_btn);

        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
//              使用前应该会有一次初始化
//  mCardContent.setmLikeAmount(mCardContent.getmLikeAmount()+1);
                onLikeAmountChanged();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                onLikeAmountChanged();
            }
        });
    }

    private void onLikeAmountChanged() {
//        mAmount.setText(String.valueOf(mCardContent.getmLikeAmount()));
    }
}
