package com.artwall.project.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.artwall.project.R;
import com.artwall.project.adapter.PhotoGraphyAdapter;
import com.artwall.project.api.IMG;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.Children;

import java.util.ArrayList;

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

    @Override
    public int getMianLayout() {
        return R.layout.fragment_photography;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.Photography_recyclerview);

        for (int i = 0; i < 10; i++) {
            Children children = new Children();
            switch (i) {
                case 1:
                    children.setImg(IMG.PIC1);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                case 3:
                    children.setImg(IMG.PIC2);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                case 5:
                    children.setImg(IMG.PIC3);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                case 6:
                    children.setImg(IMG.PIC4);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                case 7:
                    children.setImg(IMG.PIC5);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                case 9:
                    children.setImg(IMG.PIC6);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
                default:
                    children.setImg(IMG.PIC7);
                    children.setTitle("阿萨德房间卡洛斯京东方");
                    break;
            }
            list.add(children);
        }
    }

    @Override
    public void initData() {

        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        PhotoGraphyAdapter adapter = new PhotoGraphyAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

    }
}
