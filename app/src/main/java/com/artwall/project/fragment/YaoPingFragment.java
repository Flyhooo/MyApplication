package com.artwall.project.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.activity.YaoPingDetailActivity;
import com.artwall.project.adapter.YaoPingAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.YaoPing;
import com.artwall.project.widget.DividerItemDecoration;
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
public class YaoPingFragment extends BaseFragment {

    private static YaoPingFragment fragment = null;

    public static YaoPingFragment getInstance() {
        if (fragment == null) {
            return new YaoPingFragment();
        } else {
            return fragment;
        }
    }

    private RecyclerView recyclerView;
    private ArrayList<YaoPing> list = new ArrayList<>();

    private MySwipeFreshLayout swipe;
    private int page = 1;
    private int tag = 1;

    private YaoPingAdapter adapter;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_yaoping;
    }

    @Override
    public void initView() {

        recyclerView = (RecyclerView) contentView.findViewById(R.id.Yaoping_recycleview);
        swipe = (MySwipeFreshLayout) contentView.findViewById(R.id.Yaoping_swipe);

    }

    @Override
    public void initData() {

        adapter = new YaoPingAdapter(getActivity(), list);

        adapter.setOnItemClickListener(new YaoPingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), YaoPingDetailActivity.class));
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getData(page);

        // 设置下拉刷新监听器
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                tag = 1;
                page = 1;
                getData(page);
            }
        });
        // 加载监听器
        swipe.setOnLoadListener(new MySwipeFreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                tag = 2;
                page = page + 1;
                getData(page);
            }
        });

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        if (tag == 1) {
            swipe.setRefreshing(false);
        } else if (tag == 2) {
            swipe.setLoading(false);
        }
        try {
            JSONObject obj = new JSONObject(responseString);
            JSONArray data = obj.getJSONArray("data");
            Gson gson = new Gson();
            ArrayList<YaoPing> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<YaoPing>>() {
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

    @Override
    public void onDataError(String url, String responseString) {
        super.onDataError(url, responseString);
        if (tag == 1) {
            swipe.setRefreshing(false);
        } else if (tag == 2) {
            swipe.setLoading(false);
        }
    }

    private void getData(int page) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        post(API.Yaoping_List, params);
    }
}
