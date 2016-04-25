package com.artwall.project.fragment.sub;

import android.content.Intent;
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

    @Override
    public int getMianLayout() {
        return R.layout.fragment_pandw;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.PAndW_recycleview);
    }

    @Override
    public void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PaintAdapter(getActivity(), list);

        adapter.setOnItemClickListener(new PaintAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), PaintDetailActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("type", "国画");
        params.put("page", "1");
//        params.put("pageCount", "10");
        post(API.Paint_List, params);

    }

    @Override
    public void onDataOK(String url, String responseString) {
        super.onDataOK(url, responseString);
        try {
            JSONObject obj = new JSONObject(responseString);
            JSONArray data = obj.getJSONArray("data");
            Gson gson = new Gson();
            ArrayList<Paint> dataList = new ArrayList<>();
            dataList = gson.fromJson(data.toString(), new TypeToken<List<Paint>>() {
            }.getType());

            list.addAll(dataList);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
