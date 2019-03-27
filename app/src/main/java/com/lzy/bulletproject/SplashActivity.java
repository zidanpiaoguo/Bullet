package com.lzy.bulletproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.bulletproject.databinding.ActivitySplashBinding;
import com.lzy.commonbase.mvvm.base.BaseActivity;
import com.lzy.commonbase.utils.ARouterConfig;
import com.lzy.commonbase.utils.ARouterUntils;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bullet
 * @date 2019\3\19 0019.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private static final String TAG = "SplashActivity";
    /**
     * 记录进入SplashActivity的时间。
     */
    private long enterTime ;
    /**
     * 判断是否正在跳转或已经跳转到下一个界面。
     */
    private boolean isForwording = false;

    private boolean hasNewVersion = false;

    private static final int MIN_WAIT_TIME = 2;
    private static final  int MAX_WAIT_TIME = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        enterTime = System.currentTimeMillis();
    //    ARouter.getInstance().build(ARouterConfig.HOME_OK).navigation();
        delayToForward();
    }


    /**
     * 设置闪屏界面的最大延迟跳转，让用户不至于在闪屏界面等待太久。
     */
    private void delayToForward() {

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                ARouter.getInstance().build(ARouterConfig.HOME_TEST).navigation();

               // finish();

             //   ARouterUntils.navigation(SplashActivity.this, ARouterConfig.HOME_OK);

              //  MainActivity.startActivity(SplashActivity.this,MainActivity.SIGN_ONE);
            }
        },MIN_WAIT_TIME,TimeUnit.SECONDS);
    }



}
