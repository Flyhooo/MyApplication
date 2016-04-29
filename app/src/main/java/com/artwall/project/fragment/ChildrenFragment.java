package com.artwall.project.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.artwall.project.R;
import com.artwall.project.adapter.ChildrenAdapter;
import com.artwall.project.api.API;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Children;
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
 * Created by 95 on 2016/3/31.
 */
public class ChildrenFragment extends BaseFragment {

    private static ChildrenFragment fragment = null;

    public static ChildrenFragment getInstance() {
        if (fragment == null) {
            return new ChildrenFragment();
        } else {
            return fragment;
        }
    }

    private RecyclerView recyclerView;
    private ArrayList<Children> list = new ArrayList<>();

    private MySwipeFreshLayout swipe;
    private int page = 1;
    private int tag = 1;

    private ChildrenAdapter adapter;

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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //获取缓存
        if (null != RuntimeInfoService.get(getActivity(), RuntimeInfoService.CHILDREN_LIST)) {
            list = (ArrayList<Children>) RuntimeInfoService.get(getActivity(), RuntimeInfoService.CHILDREN_LIST);
        }
        //设置adapter
        adapter = new ChildrenAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

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
        getData(page);

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
            ArrayList<Children> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<Children>>() {
            }.getType());
            if (page == 1) {
                list.clear();
            }
            list.addAll(dataList);
            adapter.notifyDataSetChanged();
            RuntimeInfoService.save(getActivity(), RuntimeInfoService.CHILDREN_LIST, dataList);

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
        post(API.Children_List, params);
    }
}
