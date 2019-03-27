package com.lzy.commonbase.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.lzy.commonbase.R;
import com.lzy.commonbase.databinding.ActivityBaseNBinding;
import com.lzy.commonsdk.views.viewgroup.LoadingLayout;

/**
 * @author bullet
 * @date 2019\3\21 0021.
 */

public class BaseActivity  <T extends ViewDataBinding> extends AppCompatActivity {


    protected T bindingView;
    private ActivityBaseNBinding mBaseBinding;
    private LoadingLayout loadingLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {

        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base_n, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        loadingLayout = mBaseBinding.container;
        loadingLayout.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());
//        ARouter.getInstance().inject(this);
        //入侵式状态栏
        //  StatusBarUtil.StatusBarLightMode(this);

    }


    protected void showLoading(){
        loadingLayout.showLoading();
    }



}

