package com.lzy.bulletproject.uhome.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;

import com.lzy.bulletproject.uhome.model.HomeModel;
import com.lzy.bulletproject.uhome.view.IHomeView;

/**
 * @author bullet
 * @date 2019\1\29 0029.
 */

public class HomeVM {

    private Activity activity;
    private IHomeView iHomeView;
    private HomeModel homeModel;

    public HomeVM(Activity activity, IHomeView iHomeView) {
        this.activity = activity;
        this.iHomeView = iHomeView;
        homeModel = new HomeModel();
    }


    public void getToolsData(){
       iHomeView.backToolData(homeModel.getToolData());
    }







}
