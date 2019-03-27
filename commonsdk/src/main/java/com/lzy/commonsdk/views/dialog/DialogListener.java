package com.lzy.commonsdk.views.dialog;

import android.view.View;

/**
 * @author bullet
 * @date 2019\1\8 0008.
 */

public interface DialogListener {
    /**
     * 确认
     *
     * @param v
     */
    public void sureOnClick(View v);

    /**
     * 取消
     *
     * @param v
     */
    public void cancleOnClick(View v);
}
