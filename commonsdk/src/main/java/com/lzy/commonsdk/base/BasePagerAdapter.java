package com.lzy.commonsdk.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * TabLayout 的adapter
 *
 *  @author bullet
 *  @date 2017/12/18 0021.
 *
 */

public class BasePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment;
    private List<String> listTitle;

    /**
     * 不需要标识数据
     * @param fm   父管理
     * @param listFragment  fra
     */
    public BasePagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    public BasePagerAdapter(FragmentManager fm , List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.listFragment = fragments;
        this.listTitle = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

}
