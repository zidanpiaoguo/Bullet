package com.lzy.commonsdk.views.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 不滑动的，自适应内部高度的
 * Created by wenruiyan on 2018/10/17.
 */

public class SelfViewPager extends ViewPager {


    private boolean isScroll;

    private int current;
    private int height = 0;
    /**
     * 保存position与对于的View
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    private boolean scrollble = true;

    public SelfViewPager(Context context) {
        super(context);
    }

    public SelfViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
        // return true;不行
    }


    /**
     * 保存position与对于的View
     */
    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }


    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// 可行,消费,拦截事件
        }
    }

    /**
     * 设置是否可以滑动
     * @param scroll
     */
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = child.getMeasuredHeight();
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }


    @Override
    public void setCurrentItem(int item) {
        //去除页面切换时的滑动翻页效果
        super.setCurrentItem(item, false);
    }



}
