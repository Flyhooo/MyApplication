package com.artwall.project.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.activity.QuestionDetailActivity;
import com.artwall.project.adapter.TopicAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Topic;
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
 * 话题
 */
public class TopicFragment extends BaseFragment {

    private static TopicFragment fragment = null;

    public static TopicFragment getInstance() {
        if (fragment == null) {
            return new TopicFragment();
        } else {
            return fragment;
        }
    }

    private ArrayList<Topic> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private TopicAdapter adapter;

    private MySwipeFreshLayout swipe;
    private int page = 1;
    private int tag = 1;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_topic;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Topic_recyclerview);
        swipe = (MySwipeFreshLayout) contentView.findViewById(R.id.Topic_swipe);

    }

    @Override
    public void initData() {
        adapter = new TopicAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(activity, QuestionDetailActivity.class));
            }
        });
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

    private void getData(int page) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("pagesize", 10);
        post(API.Topic_List, params);
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
            ArrayList<Topic> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<Topic>>() {
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
