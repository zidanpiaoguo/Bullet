package com.lzy.commonsdk.views.viewpager.slideshow;

import android.support.v4.view.ViewPager;


/**
 * viewpager 的监听
 *
 *  主要是用于轮播的卡片的Viewpager 当一个卡片占满屏，正常可以使用，
 *  当一个页面有margin,显示多个卡片时，有问题再切换收尾会出现白色，（很明显），待解决。
 *
 * @author bullet
 * @date 2019\1\7 0007.
 */

public class ViewPagerListener implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    public ViewPagerListener(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int current = mViewPager.getCurrentItem();
            int lastReal = mViewPager.getAdapter().getCount()-2;
            if (current == 0) {
                mViewPager.setCurrentItem(lastReal, false);
            } else if (current == lastReal+1) {
                mViewPager.setCurrentItem(1, false);
            }
        }
    }
}
