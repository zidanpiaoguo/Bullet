package com.lzy.module_main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.commonbase.utils.ARouterConfig;
import com.lzy.module_main.R;

/**
 * @author bullet
 * @date 2019\3\22 0022.
 */
@Route(path = ARouterConfig.HOME_TEST)
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
    }
}
