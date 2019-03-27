package com.lzy.commonsdk.views.viewpager.slideshow.transformer;



import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author bullet
 * @date 2019\1\7 0007.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.8f;


    @Override
    public void transformPage(View page, float position) {
        if (position <= 1) {
            //   1.2f + (1-1)*(1.2-1.0)
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            page.setScaleX(scaleFactor);
            //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
            if (position > 0) {
                page.setTranslationX(-scaleFactor * 2);
            } else if (position <=0) {
                page.setTranslationX(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}

