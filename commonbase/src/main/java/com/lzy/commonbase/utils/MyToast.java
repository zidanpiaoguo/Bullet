package com.lzy.commonbase.utils;

import android.widget.Toast;

import com.lzy.commonbase.app.GlobalApplication;



/**
 *
 * Created by zidan on 2017/8/30.
 */
public class MyToast {
    private static String oldMsg = "";
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void makeText(String s, int duration) {
        if (s == null)
            return;
        if (toast == null) {
            toast = Toast.makeText(GlobalApplication.getInstance(), s, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
    public static void makeText(String s) {
        makeText(s, Toast.LENGTH_SHORT);
    }


    public static void makeText(int resId) {
        makeText(GlobalApplication.getInstance().getString(resId));
    }

}
