package com.baibian.activity.Lunba;


import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.bean.PeriodicalBean;
import com.baibian.tool.MeasureTools;
import com.baibian.tool.ToastTools;
import com.baibian.view.LunBa.AddSubtractView;
import com.baibian.view.LunBa.CommentCardView;
import com.baibian.view.LunBa.CustomBottomUpDialog;
import com.baibian.view.LunBa.CustomDialog;
import com.baibian.view.LunBa.ListenerScrollView;
import com.baibian.view.LunBa.RevealFollowButton;
import com.baibian.view.LunBa.SharePeriodicalDialog;
import com.baibian.view.LunBa.pullable_view.PullToRefreshLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "periodical_detail_activity";

    /**控件*/
    private ListenerScrollView mScrollView;
    private RelativeLayout mBottomNavBar;
    private LinearLayout mCommentFatherLayout;
    private TextView mDateText;//日期
    private TextView mWriteComment;//写评论（蓝字）
    private TextView mCategoryCount;//章节数
    private TextView mRecommendCount;//推荐数
    private TextView mReadCount;//阅读数
    private TextView mFansCount;//粉丝数
    private LikeButton mLikeButton;//推荐按钮
    private RelativeLayout mLoadingView;
    private ImageView mArrowCategory;
    private Toolbar mToolbar;
    private Button mPrereadButton;//免费试读按钮
    private Button mBuyButton;//购买按钮
    private CustomBottomUpDialog mCustomBottomUpDialog;
    private CustomBottomUpDialog mBuyRuleDetailDialog;
    private PullToRefreshLayout mRefreshLayout;
    private RevealFollowButton mCollectButton;
    private Handler mHandler;
    private int mBottomHeight = 0;
    private int mBottomOriginHeight = 0;
    private long mCategoryNumber = 0;
    private SharePeriodicalDialog mShareDialog;//分享Dialog


    private PeriodicalBean data= new PeriodicalBean();


    /**参数*/
    private int mCover;//封面id
    private String mTitle;//标题
    private String mBrief;//简介
    private String mDate;//日期
    private boolean mIsFocus = false;//是否收藏
    private boolean mIsRecommend = false;//是否推荐
    private boolean mIsRead = false;//是否已阅
    private long mRecommendNumber = 0;//推荐数
    private long mReadNumber = 0;//已读数
    private long mFansNumber = 0;//粉丝数
    private int mFreeChapters = 1;//免费章节数
    private int mTotalChapters =10;//总章节数
    private int mPurchaseChapters = 0;//我已购买的章节数

    private double mDiscount=9;//本章折扣
    private double mPrice=5;//每章的价格
    private double mCoins=100;//我的论道币



    /**
     * 并没有用上的东西
     */
    private List<CommentCardView.CardContent> mCardDatas = new ArrayList<>();
    private ProgressBar mProgressBar;
    private boolean isFree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_detail);

        /**
         * 初步设想是从点击时获得数据并传进来
         * 或者只传id在本activity内请求数据
         */

        Intent intentionIntent = getIntent();
        if (intentionIntent != null){
            //TODO 初始化各种数据
        }
        initData();//模拟数据

        initVariousViews();

        initNumbers();//初始化数据

        initDialogs();

        initToolbar();

        initListeners();

        initCommentContent();

    }

    /**
     * 初始化数据（模拟数据源）
     */
    private void initNumbers() {

        mCover=data.getCover();//封面id
        mTitle=data.getTitle();//标题
        mBrief=data.getBrief();//简介
        mDate=data.getDate();//日期
        mIsFocus=data.isFocus();//是否收藏
        mIsRecommend=data.isRecommend();//是否推荐
//        mIsRead=data.;//是否已阅
        mRecommendNumber = data.getReadNumber();//推荐数
        mReadNumber = data.getReadNumber();//已读数
        mFansNumber = data.getFansNumber();//粉丝数
        mFreeChapters = data.getFreeChapters();//免费章节数
        mTotalChapters =data.getTotalChapters();//总章节数
        mPurchaseChapters = data.getPurchaseChapters();//已购买章节数
        mPrice=data.getPrice();//每章的价格
        mDiscount=data.getDiscount();//折扣
//        mCoins=;//我的论道币

        onRecommendNumberChanged();//更新推荐数
        onReadNumberChanged();//更新已读数
        onFansNumberChanged();//更新粉丝数
    }



    /**
     * Toolbar
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_periodical, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_share:
                mShareDialog.show();
                break;
            case android.R.id.home://注意，这里是android.R.id.home，不能把android丢掉！
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return true;
    }

    /**
     * 模拟后端发送的数据
     * @return
     */
    private long getNumbers() {
        return 10;
    }

    private void initDialogs() {
        initShareDialog();
        initDetailDialog();
        initBuyDialog();
    }

    /**
     * 分享的dialog
     */
    private void initShareDialog() {
        mShareDialog = new SharePeriodicalDialog(this, R.style.CustomDialogTheme);
    }

    /**
     * 购买的底部dialog
     */
    private void initBuyDialog() {
        mCustomBottomUpDialog = new CustomBottomUpDialog(this, R.layout.activity_periodical_detail_buy_popup_layout) {


            private final int MAX=mTotalChapters-mFreeChapters-mPurchaseChapters;
            private TextView title;//期刊标题
            private TextView discount_state;//折扣状态
            private ImageView mCloseButton;//关闭按钮
            private AddSubtractView mBuyAmount;//购买数量框（可以增加或减少框内数量）
            private Button mBuyAllButton;//购买全部
            private Button mBuy6Button;//购买6章
            private Button mBuy12Button;//购买12章
            private TextView mSufficientAttention;//“！当前余额不足请充值”提示（余额不足时显示）
            private TextView selected_chapters_range;//购买章节的范围
            private TextView mPresentBis;//当前拥有论道币数
            private TextView mDeclaration;//购买详细说明
            private TextView mGoodsPrice;//需支付论道币数
            private Button mPayButton;//购买按钮（余额不足时改为充值）

            /**
             *判断是否应该显示提示余额不足的信息，同时余额不足时，购买按钮变成充值
             * @param count 应传入的当前的选择的总值
             */
            private void showAttentionText(int count) {
                if (Double.valueOf(mPresentBis.getText().toString()) < count * mPrice * mDiscount*0.1){
                    mSufficientAttention.setVisibility(View.VISIBLE);
                    mPayButton.setText("充值");
                }else {
                    mSufficientAttention.setVisibility(View.INVISIBLE);
                    mPayButton.setText("购买");
                }
            }

            @Override
            public void initView(CustomBottomUpDialog dialog) {

                title=(TextView) dialog.findViewById(R.id.periodical_title);
                discount_state=(TextView) dialog.findViewById(R.id.discount_state);
                mCloseButton = (ImageView) dialog.findViewById(R.id.close_button);
                mBuyAmount = (AddSubtractView) dialog.findViewById(R.id.add_subtract_periodical);
                mBuyAllButton = (Button) dialog.findViewById(R.id.buy_choice_all);
                mBuy6Button = (Button) dialog.findViewById(R.id.buy_choice_6);
                mBuy12Button = (Button) dialog.findViewById(R.id.buy_choice_12);
                mSufficientAttention = (TextView) dialog.findViewById(R.id.attention_money_insufficient);
                selected_chapters_range=(TextView) dialog.findViewById(R.id.selected_chapters_range);
                mPresentBis = (TextView) dialog.findViewById(R.id.present_money_hold);
                mDeclaration = (TextView) dialog.findViewById(R.id.buy_detail_declaration);
                mGoodsPrice = (TextView) dialog.findViewById(R.id.lun_dao_coin_count);
                mPayButton = (Button) dialog.findViewById(R.id.pay_button);

                title.setText(mTitle);//初始化标题
                discount_state.setText(String.valueOf(mDiscount)+"折");//初始化折扣状态
                mPresentBis.setText(String.valueOf(mCoins));//初始化余额
                mBuyAmount.setMAX_COUNT(MAX);//设置最大购买上限
                mBuyAmount.setOnCountChangeListener(new AddSubtractView.OnCountChangeListener() {
                    @Override
                    public void onCountChange(int count) {
                        mGoodsPrice.setText(String.valueOf(count * mPrice * mDiscount*0.1));
                        showAttentionText(count);
                        if(count==1){
                            selected_chapters_range.setText("购买第"+(mFreeChapters+mPurchaseChapters+count)+"章");
                        }else {
                            selected_chapters_range.setText("从第" + (mFreeChapters + mPurchaseChapters + 1) +
                                    "章到第" + (mFreeChapters + mPurchaseChapters + count) + "章");//更新购买章节范围
                        }
                    }
                });
                mBuyAmount.changeCount(1);
                mBuyAllButton.setOnClickListener(this);
                mBuy12Button.setOnClickListener(this);
                mBuy6Button.setOnClickListener(this);
                mDeclaration.setOnClickListener(this);
                mPayButton.setOnClickListener(this);
                mCloseButton.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buy_detail_declaration:
                        dismiss();
                        mBuyRuleDetailDialog.show();
                        break;
                    case R.id.close_button:
                        dismiss();
                        break;
                    case R.id.buy_choice_6:
                        mBuyAmount.changeCount(6);
                        break;
                    case R.id.buy_choice_12:
                        mBuyAmount.changeCount(12);
                        break;
                    case R.id.buy_choice_all:
                        mBuyAmount.changeCount(mBuyAmount.getMaxCount());
                        break;
                    case R.id.pay_button:
                        if(mPayButton.getText().equals("充值")){
                           Toast.makeText(PeriodicalDetailActivity.this,"充值！", Toast.LENGTH_SHORT).show();
                            //请在这里实现充值的逻辑
                        }else {
                            int buyamount =mBuyAmount.getmCount();
                            Toast.makeText(PeriodicalDetailActivity.this,"成功购买"+buyamount+"章！", Toast.LENGTH_SHORT).show();
                            mPurchaseChapters=mPurchaseChapters+buyamount;//计算已购买章数
                            data.setPurchaseChapters(mPurchaseChapters);//保存已购买章数信息
                            mCoins=mCoins-buyamount*mPrice*mDiscount*0.1;//更新余额
                            mPresentBis.setText(String.valueOf(mCoins));//保存余额信息
                            mBuyAmount.changeCount(1);//重置购买量、判断余额是否充足
                            mBuyAmount.setMAX_COUNT(MAX-mPurchaseChapters);//更新最大可购买章数
                            //请在这里实现购买的逻辑

                            //如果购买了全部章节，购买导航将消失
                            if(mPurchaseChapters+mFreeChapters==mTotalChapters){
                                dismiss();
                                mBottomNavBar.setVisibility(View.GONE);
                                ToastTools.ToastShow("你已经购买了所有章节");
                            }
                        }
                }
            }
        };
    }

    /**
     * 购买详情dialog
     */
    private void initDetailDialog() {
        mBuyRuleDetailDialog = new CustomBottomUpDialog(this, R.layout.activity_periodical_detail_buy_rule) {
            private ImageView mCloseButton;
            @Override
            public void initView(CustomBottomUpDialog dialog) {
                mCloseButton = (ImageView) dialog.findViewById(R.id.close_button);
                mCloseButton.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.close_button:
                        dismiss();
                        mCustomBottomUpDialog.show();
                        break;
                }
            }

        };
        mBuyRuleDetailDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mCustomBottomUpDialog.show();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListeners() {
        mRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

            }

            /**
             * 延迟1s发送加载成功信息
             * @param pullToRefreshLayout
             */
            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        mCommentFatherLayout.addView(new CommentCardView(PeriodicalDetailActivity.this));
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });
        /**
         * 后期应该向后端发送对应的数字
         */
        mCollectButton.setOnFollowedClickListener(new RevealFollowButton.OnFollowedClickListener() {
            @Override
            public void onFollowedClick(boolean isFollowed) {
                if (isFollowed){
                    if (!mIsRead) {
                        ++mReadNumber;
                        mIsRead = true;
                        onReadNumberChanged();
                    }
                    ++mFansNumber;
                    onFansNumberChanged();
                }else {
                    --mFansNumber;
                    onFansNumberChanged();
                }
            }
        });

        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ++mRecommendNumber;
                onRecommendNumberChanged();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                --mRecommendNumber;
                onRecommendNumberChanged();
            }
        });
        mBuyButton.setOnClickListener(this);
        mPrereadButton.setOnClickListener(this);
        mArrowCategory.setOnClickListener(this);
        mWriteComment.setOnClickListener(this);
        mScrollView.setOnScrollDownListener(new ListenerScrollView.OnScrollDownListener() {
            @Override
            public void onScrollDownComplete(float scrollY) {
                if (scrollY > 0){
                    Log.d("www111", mBottomHeight + "");
                    if (mBottomHeight == 0 && !isFree) {
                        startNavAnimation(0, mBottomOriginHeight);
                    }
                }else if (scrollY < 0 && !isFree){
                    Log.d("hhh111", mBottomHeight + "");
                    if (mBottomHeight == mBottomOriginHeight) {
                        startNavAnimation(mBottomHeight, 0);
                    }
                }
            }

            @Override
            public Rect onDropReleased(int offsetY, final View inner) {
                final Rect mNewLayout = new Rect();
                mLoadingView.setVisibility(View.VISIBLE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCommentFatherLayout.addView(new CommentCardView(PeriodicalDetailActivity.this));
                        mLoadingView.setVisibility(View.INVISIBLE);
                        ToastTools.ToastShow("Add Successfully");
                    }
                } ,1000);
                int measuredHeight = MeasureTools.measureWidthAndHeight(inner)[1];
                mNewLayout.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getTop() + measuredHeight);
                ToastTools.ToastShow("HHHHHHHHH");
                Log.d(TAG + 123, mNewLayout.top +" "+ mNewLayout.left);
                return mNewLayout;
            }
        });
    }

    /**
     * 可以改变数字表示的形式（比如数字过大可以用W代替万）
     */
    private void onRecommendNumberChanged() {
        mRecommendCount.setText(String.valueOf(mRecommendNumber));
    }

    private void onFansNumberChanged() {
        mFansCount.setText(String.valueOf(mFansNumber));
    }

    private void onReadNumberChanged() {
        mReadCount.setText(String.valueOf(mReadNumber));
    }

    /**
     * find各种id
     */
    private void initVariousViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        mCollectButton = (RevealFollowButton) findViewById(R.id.collect_btn);//初始化收藏按钮
        mCollectButton.setPadding(40,15,40,17);
        mCollectButton.setFollowBackground(ContextCompat.getDrawable(this, R.drawable.collect_btn_background_clicked));
        mCollectButton.setFollowTextContent(R.string.collected);
        mCollectButton.setUnFollowTextColor(Color.WHITE);
        mCollectButton.setUnFollowBackground(ContextCompat.getDrawable(this, R.drawable.collect_btn_background_unclick));
        mCollectButton.setUnFollowTvTextContent(R.string.collect);
        mDateText=(TextView)findViewById(R.id.periodical_date);//初始化日期
        mDateText.setText(mDate);
        mCategoryCount = (TextView) findViewById(R.id.category_count);//初始化章节数
        mCategoryCount.setText(String.valueOf(mTotalChapters));
        mRecommendCount = (TextView) findViewById(R.id.recommend_count);//初始化推荐数
        mRecommendCount.setText(String.valueOf(mRecommendNumber));
        mReadCount = (TextView) findViewById(R.id.read_count);//初始化已读数
        mReadCount.setText(String.valueOf(mReadNumber));
        mFansCount = (TextView) findViewById(R.id.fans_count);//初始化粉丝数
        mFansCount.setText(String.valueOf(mFansNumber));
        mLikeButton = (LikeButton) findViewById(R.id.recommend_button);//推荐按钮

        mRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);


        mArrowCategory = (ImageView) findViewById(R.id.arrow_category);//查看目录
        mLoadingView = (RelativeLayout) findViewById(R.id.loading_view_content);
        mWriteComment = (TextView) findViewById(R.id.write_comment_text);//写评论
        mScrollView = (ListenerScrollView) findViewById(R.id.scroll_view);

        mBottomNavBar = (RelativeLayout) findViewById(R.id.bottom_nav_bar);//底部导航栏（包含免费试读和购买两个按钮）
        mBuyButton = (Button) findViewById(R.id.buy_btn);//购买按钮
        mPrereadButton= (Button) findViewById(R.id.pre_read_btn);//免费试读按钮


        if (isFree||(mPurchaseChapters+mFreeChapters==mTotalChapters)){
            mBottomNavBar.setVisibility(View.GONE);//免费或已全部购买时不再显示导航栏
        }

        mCommentFatherLayout = (LinearLayout) findViewById(R.id.comment_father_view_layout);
        mHandler = new Handler();
        mBottomOriginHeight = mBottomHeight = MeasureTools.measureWidthAndHeight(mBottomNavBar)[1];
    }

    /**
     * 模拟评论数据源
     */
    private void initCommentContent() {
        for (int i = 0; i < 10; i++){
            mCommentFatherLayout.addView(new CommentCardView(this));
        }
    }

    /**
     * 导航栏动画
     * @param startY
     * @param endY
     */
    private void startNavAnimation(int startY, int endY) {
        ValueAnimator animator = ValueAnimator.ofInt(startY, endY);
        animator.setDuration(200);
//        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBottomHeight = mBottomNavBar.getLayoutParams().height = (int) animation.getAnimatedValue();
                Log.d("fff111", mBottomHeight + "");

                mBottomNavBar.requestLayout();
            }
        });
        animator.start();
    }

    /**
     * 当免费时不再显示下端导航栏
     * @param isFree
     */
    public void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.write_comment_text:
                CustomDialog dialog = new CustomDialog(this, R.style.CustomDialogTheme);
                dialog.setOnDialogDismissListener(new CustomDialog.OnDialogDismissListener() {
                    @Override
                    public void OnDismiss(final String editTextContent, boolean isPositive) {
                        if (isPositive){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    CommentCardView cardView = new CommentCardView(PeriodicalDetailActivity.this);
                                    cardView.setCommentText(editTextContent);
                                    mCommentFatherLayout.addView(cardView, 0);//在顶端添加评论
                                }
                            });
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.arrow_category:
                Intent newIntent = new Intent(PeriodicalDetailActivity.this, PeriodicalCategoryActivity.class);
                startActivity(newIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.buy_btn:
                mCustomBottomUpDialog.show();
                break;
            case R.id.pre_read_btn:
                Intent newIntent2 = new Intent(PeriodicalDetailActivity.this, PeriodicalReadActivity.class);
                startActivity(newIntent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    //模拟数据
    private void initData() {
        data.setCover(R.drawable.cover2);
        data.setTitle("第999期：逆境顺境哪个更容易成才");
        data.setBrief("如果你无法简洁地表达你的想法，那只能说明你还不够了解它.\n" +
                "                    ——阿尔伯特·爱因斯坦");
        data.setDate("2017-8-31");
        data.setRecommend(false);
        data.setFocus(false);
        data.setRecommendNumber(100);
        data.setReadNumber(100);
        data.setFansNumber(50);
        data.setDiscount(9);
        data.setFreeChapters(1);
        data.setTotalChapters(10);
        data.setPurchaseChapters(0);
        data.setPrice(5);
        }
    private void initCoins(){

    }

    }
