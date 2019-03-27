package com.lzy.bulletproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.bulletproject.R;
import com.lzy.commonbase.utils.ARouterConfig;

/**
 * @author bullet
 * @date 2019\3\22 0022.
 */
@Route( path = ARouterConfig.HOME_NEW)
public class TestArouterActivity  extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcl);
    }
}
