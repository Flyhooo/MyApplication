package com.artwall.project.fragment.sub;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.artwall.project.R;
import com.artwall.project.activity.PaintDetailActivity;
import com.artwall.project.adapter.PaintAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Paint;
import com.artwall.project.service.RuntimeInfoService;
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
 * Created by 95 on 2016/3/30.
 */
public class SubPaintFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Paint> list = new ArrayList<>();
    private PaintAdapter adapter;
    private String id;
    private MySwipeFreshLayout swipe;
    private int page = 1;
    /**
     * 刷新  加载标志位 1刷新 2加载更多
     */
    private int tag = 1;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_pandw;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Paint_recycleview);
        swipe = (MySwipeFreshLayout) contentView.findViewById(R.id.Paint_swipe);
        swipe.setColorSchemeResources(R.color.app_black, R.color.app_green);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (null != RuntimeInfoService.get(getActivity(), RuntimeInfoService.PAINT_LIST + id)) {
            list = (ArrayList<Paint>) RuntimeInfoService.get(getActivity(), RuntimeInfoService.PAINT_LIST + id);
        }

        adapter = new PaintAdapter(getActivity(), list);
        adapter.setOnItemClickListener(new PaintAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), PaintDetailActivity.class));
            }
        });
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
            ArrayList<Paint> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<Paint>>() {
            }.getType());
            if (page == 1) {
                list.clear();
            }
            list.addAll(dataList);
            adapter.notifyDataSetChanged();
            RuntimeInfoService.save(getActivity(), RuntimeInfoService.PAINT_LIST + id, dataList);

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
        params.put("fication", id);
        params.put("page", page);
        params.put("pageSize", "10");
        post(API.Paint_List, params);
    }
}
