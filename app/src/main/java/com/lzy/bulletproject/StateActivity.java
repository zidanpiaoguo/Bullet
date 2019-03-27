package com.lzy.bulletproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.commonbase.utils.ARouterConfig;

/**
 * @author bullet
 * @date 2019\3\25 0025.
 */

public class StateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ARouter.getInstance().build(ARouterConfig.HOME_OK).navigation();
    }
}
