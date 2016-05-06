package com.artwall.project.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.artwall.project.R;
import com.artwall.project.adapter.fragmentAdapter.PaintAndWriteFragmentAdapter;
import com.artwall.project.base.BaseActivity;
import com.artwall.project.bean.PaintDetail;
import com.artwall.project.fragment.sub.SubPaintFragment;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/8.
 */
public class UserCenterActivity extends BaseActivity {

    private ArrayList<Fragment> fragmentList;
    private TabLayout tabs;
    private ArrayList<PaintDetail> list = new ArrayList<>();
    private PaintAndWriteFragmentAdapter adapter;
    private ViewPager viewPager;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_usercenter;
    }

    @Override
    protected void initGui() {
        toolbar = (Toolbar) this.findViewById(R.id.common_toolbar);
        toolbar.setTitle("史蒂芬森");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabs = (TabLayout) this.findViewById(R.id.UserCenter_tabs);
        viewPager= (ViewPager) this.findViewById(R.id.UserCenter_viewpager );


    }

    @Override
    protected void initData() {
        String[] titles = getResources().getStringArray(R.array.usercenter_title);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragmentList.add(new SubPaintFragment());
        }
//        adapter = new PaintAndWriteFragmentAdapter(getSupportFragmentManager(), titles, fragmentList);
//        viewPager.setAdapter(adapter);
//        tabs.setupWithViewPager(viewPager);
//        tabs.setTabsFromPagerAdapter(adapter);


    }

}
