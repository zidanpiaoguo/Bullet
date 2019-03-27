package com.lzy.bulletproject.uhome.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseFragment;
import com.lzy.bulletproject.databinding.FragmentHomeBinding;
import com.lzy.bulletproject.uhome.adapter.HomeToolAdapter;
import com.lzy.bulletproject.uhome.child.recyclerview.RclActivity;
import com.lzy.bulletproject.uhome.data.ToolData;
import com.lzy.bulletproject.uhome.viewmodel.HomeVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements AppBarLayout.OnOffsetChangedListener,IHomeView{
    private static final String TAG = "HomeFragment";

    private static HomeFragment mHomeFragment;
    private HomeToolAdapter homeToolAdapter;
    private List<ToolData> tools = new ArrayList<>();
    private HomeVM homeVM;
    /**
     * 双重锁单例
     * @return
     */
    public static HomeFragment getFragment() {
        if (mHomeFragment == null) {
            synchronized (HomeFragment.class) {
                if (mHomeFragment == null) {
                    Bundle args = new Bundle();
                    mHomeFragment = new HomeFragment();
                    mHomeFragment.setArguments(args);
                }
            }
        }
        return mHomeFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        homeVM = new HomeVM(mActivity,this);
        isPrepared = true;
        initView();
        loadData();
    }




    private void initView() {
        bindingView.appBar.addOnOffsetChangedListener(this);
        homeToolAdapter = new HomeToolAdapter(R.layout.item_home_tools,tools);

        bindingView.rclTools.setLayoutManager(new GridLayoutManager(mActivity,4));
        bindingView.rclTools.setAdapter(homeToolAdapter);
        homeToolAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 1:
                        Intent intent = new Intent(mActivity, RclActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
            }
        });


    }


    @Override
    protected void loadData() {
        super.loadData();
        homeVM.getToolsData();
    }

    /**
     * 变换标题栏的颜色
     * @param appBarLayout
     * @param verticalOffset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {
            //当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            bindingView.toolbarOn.getRoot().setVisibility(View.VISIBLE);
            bindingView.toolbarBelow.getRoot().setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            bindingView.toolbarOn.openShade.setBackgroundColor(Color.argb(alpha2, 18, 150, 219));
        } else {
            //当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            bindingView.toolbarBelow.getRoot().setVisibility(View.VISIBLE);
            bindingView.toolbarOn.getRoot().setVisibility(View.GONE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            bindingView.toolbarBelow.closeShade.setBackgroundColor(Color.argb(alpha3, 18, 150, 219));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        bindingView.superior.bgContent.setBackgroundColor(Color.argb(alpha, 18, 150, 219));
    }


    @Override
    public void backToolData(List<ToolData> data) {
        tools.clear();
        tools.addAll(data);
        homeToolAdapter.notifyDataSetChanged();
    }
}
