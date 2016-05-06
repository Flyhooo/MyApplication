package com.artwall.project.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.adapter.PhotoGraphyAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Meet;
import com.artwall.project.widget.MySwipeFreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    private PhotoGraphyAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Meet> list = new ArrayList<>();
    private MySwipeFreshLayout swipe;
    private int page = 1;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_photography;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Photography_recyclerview);
        swipe = (MySwipeFreshLayout) contentView.findViewById(R.id.Photography_swipe);

    }

    @Override
    public void initData() {

        //设置layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        adapter = new PhotoGraphyAdapter(getActivity(), list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhotoGraphyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

        getData(page);
        // 设置下拉刷新监听器
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getData(page);
            }
        });
        // 加载监听器
        swipe.setOnLoadListener(new MySwipeFreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                page = page + 1;
                getData(page);
            }
        });
    }

    private void getData(int page) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("pagesize", 10);
        post(API.Meeting_List, params);

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        swipe.setRefreshing(false);
        try {
            JSONObject obj = new JSONObject(responseString);
            JSONArray data = obj.getJSONArray("data");
            Gson gson = new Gson();
            ArrayList<Meet> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<Meet>>() {
            }.getType());
            if (page == 1) {
                list.clear();
            }
            list.addAll(dataList);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
