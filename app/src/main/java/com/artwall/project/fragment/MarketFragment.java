package com.artwall.project.fragment;

import com.artwall.project.R;
import com.artwall.project.base.BaseFragment;

/**
 * Created by 95 on 2016/3/31.
 */
public class MarketFragment extends BaseFragment {

    private static MarketFragment fragment = null;

    public static MarketFragment getInstance() {
        if (fragment == null) {
            return new MarketFragment();
        } else {
            return fragment;
        }
    }

    @Override
    public int getMianLayout() {
        return R.layout.fragment_market;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
