package com.lzy.commonsdk.views.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class NoSlideViewPager extends ViewPager{

    public NoSlideViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //去掉ViewPager默认的滑动效果， 不消费事件
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //不让拦截事件
        return false;
    }
}
