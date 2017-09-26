package com.baibian.fragment.messy_fiction;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baibian.R;
import com.baibian.activity.user_drawer.MessageAndNotificationActivity;
import com.baibian.bean.MessageAndNotificationMessage.MessageFriendRightItem;
import com.baibian.bean.MessageAndNotificationMessage.MessageSolidItem;
import com.baibian.bean.MessageAndNotificationMessage.MultipleTypes;
import com.baibian.tool.DataTools;
import com.baibian.tool.RecyclerViewCommonTool.CommonAdapter;
import com.baibian.tool.RecyclerViewCommonTool.ViewHolder;
import com.baibian.tool.ToastTools;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupChatFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mLeftRecyclerView;
    private RecyclerView mRightRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mFailedText;
    private Button mInvovleBtn;

    private List<String> mLeftData = new ArrayList<>();
    private List<MessageFriendRightItem> mRightData = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private int selectedPosition = -1;

    public GroupChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupChatFragment newInstance() {
        GroupChatFragment fragment = new GroupChatFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mLeftData = MessageAndNotificationActivity.mFriendsLeftDatea;
        mRightData = MessageAndNotificationActivity.mFriendsRightData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_chat, container, false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_item_decoration_drawable));

        mLeftRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_group_chat_list);
        mRightRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_group_chat_right_list);
        mInvovleBtn = (Button) rootView.findViewById(R.id.fragment_group_chat_involve_btn);
        mFailedText = (TextView) rootView.findViewById(R.id.fragment_group_chat_notice_fail);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_group_chat_load_bar);

        mInvovleBtn.setVisibility(View.GONE);
        mInvovleBtn.setOnClickListener(this);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftRecyclerView.addItemDecoration(itemDecoration);
        mLeftRecyclerView.setAdapter(new CommonAdapter<String>(getContext(), R.layout.fragment_friends_simple_string, mLeftData){
            TextView titleView;
            View contentView;
//            selectedPosition = -1;
            private void notifyRightPositionChanged(final int currentPosition) {
                Runnable updateDataSet = new Runnable() {
                    public void run() {
                        if (requestRightSideData(currentPosition)) {
                            mRightRecyclerView.getAdapter().notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                            mFailedText.setVisibility(View.GONE);
                            mRightRecyclerView.setVisibility(View.VISIBLE);
                            mInvovleBtn.setVisibility(View.VISIBLE);
                        } else {
                            ToastTools.ToastShow("加载失败");
                            mProgressBar.setVisibility(View.GONE);
                            mFailedText.setVisibility(View.VISIBLE);
                            mInvovleBtn.setVisibility(View.GONE);
                        }
                    }
                };
                mFailedText.setVisibility(View.GONE);
                mRightRecyclerView.setVisibility(View.GONE);
                mInvovleBtn.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(updateDataSet, 500);
            }

            @Override
            public void convert(ViewHolder holder, String s) {
                final int currentPosition = holder.getLayoutPosition();
                titleView = (TextView) holder.getItemView().findViewById(R.id.fragment_friends_string_text);
                contentView = holder.getItemView();
                if (currentPosition == selectedPosition){
                    contentView.setBackgroundColor(Color.parseColor("#a197a1"));
                }else {
                    contentView.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedPosition = currentPosition;
                        notifyDataSetChanged();
                        notifyRightPositionChanged(currentPosition);
                    }
                });

            }
        });
        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightRecyclerView.addItemDecoration(itemDecoration);
        mRightRecyclerView.setAdapter(new CommonAdapter<MessageFriendRightItem>(getContext(), R.layout.fragment_friends_right_layout, mRightData){

            private CircleImageView userPortrait;
            @Override
            public void convert(ViewHolder holder, MessageFriendRightItem messageFriendRightItem) {
                BadgeView badgeView = new BadgeView(getContext());
                userPortrait = (CircleImageView) holder.getItemView().findViewById(R.id.friends_right_user_portrait);
                badgeView.setTargetView(userPortrait);
                //主要是改变原有的badge外观，变成纯红点的形式
                badgeView.setText("1");
                badgeView.setTextColor(Color.parseColor("#d3321b"));
                badgeView.setTextSize(8.0F);
                badgeView.setPadding(DataTools.dip2px(getContext(), 3), 0, DataTools.dip2px(getContext(), 3) ,0 );

            }
        });
        return rootView;
    }

    private boolean requestRightSideData(int currentPosition) {
        List<MessageFriendRightItem> tempItemList = new ArrayList<>();
        //只能操作传入对应adapter的data对象
        mRightData.clear();
        //模拟数据更新
        for (int i = 0; i < currentPosition + 3; i++){
            tempItemList.add(new MessageFriendRightItem());
        }
        for (MessageFriendRightItem item : tempItemList){
            mRightData.add(item);
        }
        Random random = new Random();
//        ToastTools.ToastShow(currentPosition + "");
        return random.nextBoolean();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(new MessageSolidItem());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_group_chat_involve_btn:
                transitToGroupChatFace(selectedPosition);
                break;
        }
    }

    /**
     *用来向后端传递选择的id来跳转到正确的群聊界面
     * @param selectedPosition 表示左侧选中的item位置
     */
    private void transitToGroupChatFace(int selectedPosition) {
        ToastTools.ToastShow(selectedPosition + "");
        //TODO to send user id to back to transit to group chat interface
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(MultipleTypes typeItem);
    }
}
