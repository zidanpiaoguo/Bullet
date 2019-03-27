package com.lzy.commonsdk.utils.display;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Spring on 2017/7/3.
 * Toast
 */

public class ToastUtils {

    private static Toast layoutToast;


    private static String oldMsg = "";
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void makeText(Context mContext,String s) {
        if (s == null)
            return;
        if (toast == null) {
            toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
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


    /**
     * duration：0-短显示  1-长显示
     */
    public static void showShort(Context context, String content, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        if (duration == 0) {
            duration = Toast.LENGTH_SHORT;
        } else {
            duration = Toast.LENGTH_LONG;
        }
        toast = Toast.makeText(context, content, duration);
        toast.show();
    }

    /**
     * 设置Toast位置
     */
    public static void showLayoutLong(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        if (layoutToast != null) {
            layoutToast.cancel();
        }
        if (context != null) {
            layoutToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            layoutToast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            layoutToast.show();
        }
    }




}
