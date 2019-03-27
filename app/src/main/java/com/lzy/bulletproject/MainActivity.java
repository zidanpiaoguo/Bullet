package com.lzy.bulletproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.bulletproject.base.mvvm.BaseActivity;
import com.lzy.bulletproject.data.TabEntity;
import com.lzy.bulletproject.databinding.ActivityMainBinding;
import com.lzy.bulletproject.uchat.view.ChatFragment;
import com.lzy.bulletproject.uhome.view.HomeFragment;
import com.lzy.bulletproject.umine.view.MineFragment;
import com.lzy.bulletproject.umoney.view.MoneyFragment;
import com.lzy.bulletproject.ushopping.view.ShopFragment;
import com.lzy.commonbase.utils.ARouterConfig;
import com.lzy.commonsdk.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页，入口我并没有严格分为MVVM ，
 *
 * @author bullet
 * @date 2019\1\21 0021.
 */
@Route(path = ARouterConfig.HOME_OK)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static final int SIGN_ONE = 1;
    List<Fragment> fragments = new ArrayList<>();

    private int[] mTitle = {R.string.bot_tv1,R.string.bot_tv2,R.string.bot_tv3,R.string.bot_tv4,R.string.bot_tv5};
    private int[] mIconSelect = {R.mipmap.btm_sel_icon1,R.mipmap.btm_sel_icon2,R.mipmap.btm_sel_icon3,R.mipmap.btm_sel_icon4,R.mipmap.btm_sel_icon5};
    private int[] mIconUnSelect = {R.mipmap.btm_unsel_icon1,R.mipmap.btm_unsel_icon2,R.mipmap.btm_unsel_icon3,R.mipmap.btm_unsel_icon4 ,R.mipmap.btm_unsel_icon5};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    /**
     * 启动的方法
     * @param context 启动项
     * @param sign    标志
     */
    public static void startActivity(Context context,int sign){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("sign",sign);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        fragments.add(HomeFragment.getFragment());
        fragments.add(MoneyFragment.getFragment());
        fragments.add(ShopFragment.getFragment());
        fragments.add(ChatFragment.getFragment());
        fragments.add(MineFragment.getFragment());
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(),fragments);
        bindingView.vpFragment.setAdapter(pagerAdapter);
        bindingView.vpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bindingView.bottomTab.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bindingView.vpFragment.setCurrentItem(0);
    }

    private void initTabLayout() {
        for (int i = 0; i < mTitle.length; i++) {
            mTabEntities.add(new TabEntity(getResources().getString(mTitle[i]),mIconSelect[i],mIconUnSelect[i]));
        }
        bindingView.bottomTab.setTabData(mTabEntities);

        bindingView.bottomTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                bindingView.vpFragment.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                //可以是先重复点击刷新 eventbus
            }
        });
    }


}
