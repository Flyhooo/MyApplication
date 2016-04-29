package com.artwall.project.adapter.fragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/3/30.
 */
public class PaintAndWriteFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> mtitles;
    private ArrayList<Fragment> mFragments;

    public PaintAndWriteFragmentAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
        super(fm);
        this.mtitles = titles;
        this.mFragments = fragments;

    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtitles.get(position);
    }
}
