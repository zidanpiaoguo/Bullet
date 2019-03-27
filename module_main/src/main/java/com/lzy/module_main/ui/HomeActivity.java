package com.lzy.module_main.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.commonbase.mvvm.base.BaseActivity;
import com.lzy.commonbase.utils.ARouterConfig;
import com.lzy.module_main.R;
import com.lzy.module_main.databinding.ActivityMainHomeBinding;

/**
 * @author bullet
 * @date 2019\3\21 0021.
 */

public class HomeActivity extends BaseActivity<ActivityMainHomeBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
      //  ARouter.getInstance().inject(this);
    }
}
