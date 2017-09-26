package com.baibian.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baibian.R;
import com.baibian.activity.Lunba.FindQuestionsActivity;
import com.baibian.activity.Lunba.PeriodicalMainActivity;
import com.baibian.activity.Lunba.RankingListActivity;


public class FindFragment extends Fragment  implements View.OnClickListener {

    private ImageView periodical;
    private ImageView good_sentences;
    private ImageView ranking_list;
    private ImageView find_questions;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View FindFragment = inflater.inflate(R.layout.find_layout, container, false);
        periodical=(ImageView)FindFragment.findViewById(R.id.iv_periodical);
        good_sentences=(ImageView)FindFragment.findViewById(R.id.iv_good_sentences);
        ranking_list=(ImageView)FindFragment.findViewById(R.id.iv_ranking_list);
        find_questions=(ImageView)FindFragment.findViewById(R.id.iv_find_questions);
        periodical.setOnClickListener(this);
        good_sentences.setOnClickListener(this);
        ranking_list.setOnClickListener(this);
        find_questions.setOnClickListener(this);
        return FindFragment;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_periodical:
                Intent intent1 = new Intent(getActivity(), PeriodicalMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_find_questions:
                Intent intent2 = new Intent(getActivity(),FindQuestionsActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_good_sentences:
//                Intent intent3 = new Intent(getActivity(), PeriodicalMainActivity.class);
//                startActivity(intent3);
                break;
            case R.id.iv_ranking_list:
                Intent intent4 = new Intent(getActivity(), RankingListActivity.class);
                startActivity(intent4);
                break;

        }
    }
}
