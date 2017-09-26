package com.baibian.activity.Lunba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baibian.R;
import com.baibian.bean.PeriodicalBean;
import com.baibian.tool.superadapter.DataHolder;
import com.baibian.tool.superadapter.LayoutWrapper;
import com.baibian.tool.superadapter.SuperAdapter;
import com.baibian.tool.superadapter.SuperViewHolder;
import com.baibian.view.LunBa.SlideShowView.SlideShowView;

import java.util.ArrayList;
import java.util.List;

public class MainRecycleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView = null;//缓存Fragment view

    private SuperAdapter adapter;
    private  List<LayoutWrapper> data;
    DataHolder<SliderHolder> sliderHolder;
    private class SliderHolder {}
    DataHolder<TitleItemHolder> titleItemHolder;
    private class TitleItemHolder {
        String title;
        String type;
        public TitleItemHolder(String title,String type) {
            this.title=title;
            this.type=type;
        }
        public String getTitle() {
            return title;
        }
        public String getType() {
            return type;
        }
    }
    DataHolder<PeriodicalBean> firstItemHolder;
    DataHolder<PeriodicalBean> normalItemHolder;
    private LinearLayoutManager mLinearLayoutManager;


    private SwipeRefreshLayout lay_fresh;
    private Context mContext;
    private List<PeriodicalBean> data_weekly,data_hero,data_subject;

    //定义回调接口
    public interface OnMyClickListener {
         void Todetail(String type) ;
    }
    OnMyClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(OnMyClickListener)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_periodical_main_fragment, container, false);
        initBase();
        mContext=getContext();
        return rootView;

    }

    private void initBase() {
        lay_fresh = (SwipeRefreshLayout) this.rootView.findViewById(R.id.lay_refresh);
        lay_fresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        lay_fresh.setOnRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) this.rootView.findViewById(R.id.recyclerView);


        data_weekly = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover1);
            data.setBrief("如果你无法简洁地表达你的想法，那只能说明你还不够了解它.\n" +
                    "                    ——阿尔伯特·爱因斯坦");
            data.setReadNumber(7);
            data.setTitle("第999期：逆境顺境哪个更容易成才");
            data_weekly.add(data);
        }
        data_hero = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover2);
            data.setBrief("...");
            data.setReadNumber(7);
            data.setTitle("第999期");
            data_hero.add(data);
        }
        data_subject = new ArrayList<PeriodicalBean>();
        for (int i = 0; i < 10; i++) {
            PeriodicalBean data = new PeriodicalBean();
            data.setCover(R.drawable.cover3);
            data.setBrief("...");
            data.setReadNumber(7);
            data.setTitle("第999期");
            data_subject.add(data);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

        data = new ArrayList<>();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return data.get(position).getSpanSize();
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

         int[] layoutIds ={
                 R.layout.activity_periodical_main_item_slider,
                 R.layout.activity_periodical_main_item_title,
                 R.layout.activity_periodical_main_item_first,
                 R.layout.activity_periodical_main_item_normal};
        adapter = new SuperAdapter(getContext(), layoutIds);
        recyclerView.setAdapter(adapter);

        sliderHolder =new DataHolder<SliderHolder>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, SliderHolder item, int position) {
                String img = "http://pic16.nipic.com/20110921/7247268_215811562102_2.jpg";
                String[] imgs= new String[]{img,img,img,img,img,img,img};
                final SlideShowView slideShowView= holder.getView(R.id.slideShowView);
                slideShowView.setImageUrls(imgs);
                slideShowView.startPlay();
            }
        };

        titleItemHolder =new DataHolder<TitleItemHolder>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, final TitleItemHolder item, int position) {
                final TextView weekly =  holder.getView(R.id.weekly);
                weekly.setText(item.getTitle());
                final TextView more = holder.getView(R.id.more);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.Todetail(item.getType());
                    }
                });
            }
        };

        firstItemHolder =new DataHolder<PeriodicalBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, PeriodicalBean item, final int position) {
                final ImageView cover =  holder.getView(R.id.type_top_cover);
                final TextView title = holder.getView(R.id.type_top_title);
                final TextView brief= holder.getView(R.id.type_top_brief);
                cover.setImageResource(item.getCover());
                title.setText(item.getTitle());
                brief.setText(item.getBrief());
                if(adapter.getmOnItemClickListener()!=null) {
                    holder.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.getmOnItemClickListener().OnItemClick(v, position);
                        }
                    });
                }

            }
        };

        normalItemHolder=new DataHolder<PeriodicalBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, PeriodicalBean item, final int position) {
                final ImageView cover =  holder.getView(R.id.type_cover);
                final TextView title = holder.getView(R.id.type_title);
                cover.setImageResource(item.getCover());
                title.setText(item.getTitle());
                if(adapter.getmOnItemClickListener()!=null) {
                    holder.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.getmOnItemClickListener().OnItemClick(v, position);
                        }
                    });
                }
            }
        };


        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_slider, 3,null, sliderHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_title, 3,new TitleItemHolder("每周集锦","weekly"), titleItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_first, 3,data_weekly.get(0), firstItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_weekly.get(1), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_weekly.get(2),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_weekly.get(3), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_title, 3,new TitleItemHolder("群英荟萃","hero"), titleItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(0), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(1),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(2), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(3), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(4),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_hero.get(5), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_title, 3,new TitleItemHolder("辩题集锦","subject"), titleItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(0), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(1),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(2), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(3), normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(4),normalItemHolder));
        data.add(new LayoutWrapper(R.layout.activity_periodical_main_item_normal, 1,data_subject.get(5), normalItemHolder));
        adapter.setData(data);
        adapter.setmOnItemClickLitener(new SuperAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(getContext(), "item"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),PeriodicalDetailActivity.class);
                startActivity(intent);
            }
        });

//
//        adapter=new PeriodicalMainRecycleAdapter(data_weekly,data_hero,data_subject,getActivity());
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickLitener(new PeriodicalMainRecycleAdapter.OnItemClickListener() {
//
//            @Override
//            public void OnItemClick(View view,int position, String type) {
//                if(type=="TypeHead")
//                {
//                    Toast.makeText(mContext, position+"TypeHead", Toast.LENGTH_SHORT).show();
//                    switch (position) {
//                        case 1:
//                            listener.Todetail("weekly");
//                            break;
//                        case 6:
//                            listener.Todetail("hero");
//                            break;
//                        case 13:
//                            listener.Todetail("subject");
//                    }
//
//                }else if(type=="Topitem")
//                {
//                    Toast.makeText(mContext, position+"Topitem", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getContext(), PeriodicalDetailActivity.class);
//                    startActivity(intent);
//                }else if(type=="item")
//                {
//                    Toast.makeText(mContext, position+"item", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getContext(),PeriodicalDetailActivity.class);
//                    startActivity(intent);
//                }
//            }


//        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lay_fresh.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
