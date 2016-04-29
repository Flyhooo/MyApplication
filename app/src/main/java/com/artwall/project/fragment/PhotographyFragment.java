package com.artwall.project.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.adapter.PhotoGraphyAdapter;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Children;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/31.
 */
public class PhotographyFragment extends BaseFragment {

    private static PhotographyFragment fragment = null;

    public static PhotographyFragment getInstance() {
        if (fragment == null) {
            return new PhotographyFragment();
        } else {
            return fragment;
        }
    }

    private RecyclerView recyclerView;
    private ArrayList<Children> list = new ArrayList<>();

    @Override
    public int getMianLayout() {
        return R.layout.fragment_photography;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Photography_recyclerview);

        for (int i = 0; i < 10; i++) {
            Children children = new Children();
            children.setTitle("阿萨德房间卡洛斯京东方");
            list.add(children);
        }
    }

    @Override
    public void initData() {

        //设置layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        PhotoGraphyAdapter adapter = new PhotoGraphyAdapter(getActivity(), list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhotoGraphyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

    }
}
