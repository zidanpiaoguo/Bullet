package com.lzy.commonbase.mvvm.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lzy.commonbase.R;
import com.lzy.commonbase.databinding.FragmentBaseNBinding;
import com.lzy.commonsdk.views.viewgroup.LoadingLayout;

/**
 * @author bullet
 * @date 2019\3\21 0021.
 */

public abstract class BaseFragment <T extends ViewDataBinding> extends Fragment {

    private static final String TAG = "BaseFragment";

    private FragmentBaseNBinding mBaseBinding;
    protected LoadingLayout mContainer;
    protected T bindingView;
    protected Activity mActivity;
    protected boolean isVisible;
    /**
     * 在onCreateView 之后要改为ture
     */
    protected boolean isPrepared;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //下面几个的构成参数不要写错
        View ll = inflater.inflate(R.layout.fragment_base_n,null);
        bindingView = DataBindingUtil.inflate(mActivity.getLayoutInflater(), setLayoutId(), null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        mContainer =  ll.findViewById(R.id.container) ;
        mContainer.addView(bindingView.getRoot());
        Log.d(TAG, "onCreateView: "+bindingView.getClass());
        return ll;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: "+bindingView.getClass());
    }

    /**
     * 初始化布局文件
     * @return
     */
    protected abstract int setLayoutId();


    /**
     * 懒加载 这个是在onCreateView 之前调用的，所以第一页调用一下，
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 不可见的时候调用
     */
    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {

    }

    protected void onVisible() {
        if(!isVisible ||!isPrepared){
            return;
        }
        loadData();
    }


}

