package com.lzy.commonbase.app;

import android.app.Application;

/**
 * @author bullet
 * @date 2019\3\19 0019.
 */

public abstract class GlobalApplication  extends Application{

    private static GlobalApplication application;

    public static GlobalApplication getInstance() {

        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }


    /**
     * 初始化 控件
     */
    public abstract void init();


}
