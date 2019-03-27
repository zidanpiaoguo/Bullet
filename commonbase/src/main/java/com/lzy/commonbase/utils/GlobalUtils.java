package com.lzy.commonbase.utils;

/**
 * @author bullet
 * @date 2019\3\19 0019.
 */

public class GlobalUtils {

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
