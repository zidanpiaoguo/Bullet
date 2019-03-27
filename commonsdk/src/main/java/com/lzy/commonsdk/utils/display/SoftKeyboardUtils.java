package com.lzy.commonsdk.utils.display;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by LH on 2017/12/7.
 * Description ：软键盘，隐藏
 */

public class SoftKeyboardUtils {

    private static final long SHOW_SPACE = 200L;


    /**
     * 显示软键盘
     */
    public static void showSoftInput(final View view) {
        if (view == null || view.getContext() == null) { return;}
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, SHOW_SPACE);
    }

    /**
     * 隐藏软键盘
     * @param activity
     * @param editText
     */
    public static void hideSoftkeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }






}
