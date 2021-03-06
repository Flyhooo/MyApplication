package com.artwall.project.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.artwall.project.R;
import com.artwall.project.adapter.fragmentAdapter.PaintAndWriteFragmentAdapter;
import com.artwall.project.base.BaseFragment;
import com.artwall.project.bean.PaintType;
import com.artwall.project.fragment.sub.SubPaintFragment;
import com.artwall.project.service.RuntimeInfoService;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/30.
 */
public class PaintFragment extends BaseFragment {

    private static PaintFragment fragment = null;

    public static PaintFragment getInstance() {
        if (fragment == null) {
            return new PaintFragment();
        } else {
            return fragment;
        }
    }

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = null;
    private TabLayout tabs;
    private ViewPager viewPager;
    private PaintAndWriteFragmentAdapter adapter;

    @Override
    public int getMianLayout() {
        return R.layout.fragment_paintandwrite;
    }

    @Override
    public void initView() {
        tabs = (TabLayout) contentView.findViewById(R.id.PaintAndWrite_tab);
        viewPager = (ViewPager) contentView.findViewById(R.id.PaintAndWrite_viewpager);
    }

    @Override
    public void initData() {
//        titles = getActivity().getResources().getStringArray(
//                R.array.paint_title);

        ArrayList<PaintType> paintTypeList = RuntimeInfoService.getPaintTypeList(activity);
        for (int i = 0; i < paintTypeList.size(); i++) {
            titles.add(paintTypeList.get(i).getKeyname());
        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            SubPaintFragment fragment = new SubPaintFragment();
            fragment.setId(paintTypeList.get(i).getId());
            fragmentList.add(fragment);
        }
        adapter = new PaintAndWriteFragmentAdapter(getChildFragmentManager(), titles, fragmentList);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(adapter);

    }
}
