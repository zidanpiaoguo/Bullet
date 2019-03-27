package com.lzy.commonsdk.utils;

/**
 * @author bullet
 * @date 2019\1\29 0029.
 */

public class CommentUtils {



    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    /**
     * 重复性点击判断
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }





}
