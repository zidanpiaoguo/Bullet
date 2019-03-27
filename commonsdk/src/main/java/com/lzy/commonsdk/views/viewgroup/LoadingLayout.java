package com.lzy.commonsdk.views.viewgroup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.commonsdk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * * 三种状态的LinearLayout，用于切换当前的页面显示
 * 出错 ERROR, 成功 CONTENT, 加载中 LOADING
 *
 * @author bullet
 * @date 2019\1\21 0021.
 */

public class LoadingLayout extends RelativeLayout {
    private static final String LOADING_TAG = "loading_tag";
    private static final String ERROR_TAG = "error_tag";


    private Context mContext;
    private LayoutParams layoutParams;
    private LayoutInflater layoutInflater;
    private LinearLayout loadingView, errorView;

    private TextView btn_error;

    private List<View> contentViews = new ArrayList<>();
    private RotateAnimation rotateAnimation;
    private State currentState = State.LOADING;


    public LoadingLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        //是否是 非内容view
        boolean isChild = child.getTag() == null || (!child.getTag().equals(LOADING_TAG) && !child.getTag().equals(ERROR_TAG));
        if (isChild) {
            contentViews.add(child);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (btn_error != null) {
            btn_error.setOnClickListener(null);
        }
    }


    /**
     * 显示加载view
     */
    public void showLoading() {
        currentState = State.LOADING;
        this.showLoadingView();
        this.hideErrorView();
        this.setContentVisibility(false);
    }

    /**
     * 显示加载中布局
     */
    private void showLoadingView() {
        if (loadingView == null) {
            loadingView = (LinearLayout) layoutInflater.inflate(R.layout.view_loading, null);
            loadingView.setTag(LOADING_TAG);
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            ImageView iv_loading = (ImageView) loadingView.findViewById(R.id.iv_loading);
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(800);
            //重复
            rotateAnimation.setRepeatMode(Animation.RESTART);
            //无限旋转
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.start();
            //线性差值器,一直匀速旋转
            LinearInterpolator lir = new LinearInterpolator();
            rotateAnimation.setInterpolator(lir);
            iv_loading.startAnimation(rotateAnimation);
            this.addView(loadingView, layoutParams);
        } else {
            rotateAnimation.start();
            loadingView.setVisibility(VISIBLE);
        }
    }

    private void hideErrorView() {
        if (errorView != null && errorView.getVisibility() != GONE) {
            errorView.setVisibility(GONE);
        }
    }

    public void setContentVisibility(boolean visible) {
        for (View contentView : contentViews) {
            contentView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public void showContent() {
        currentState = State.CONTENT;
        this.setContentVisibility(true);
        this.hideLoadingView();
        this.hideErrorView();
    }

    private void hideLoadingView() {
        if (loadingView != null && loadingView.getVisibility() != GONE) {
            loadingView.setVisibility(GONE);
            rotateAnimation.cancel();
        }
    }

    public void showError(OnClickListener click) {
        currentState = State.ERROR;
        this.hideLoadingView();
        this.showErrorView();
        this.btn_error.setOnClickListener(click);
        this.hideContentView();
    }

    private void showErrorView() {
        if (errorView == null) {
            errorView = (LinearLayout) layoutInflater.inflate(R.layout.view_error, null);
            errorView.setTag(ERROR_TAG);

            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(VISIBLE);
        }
    }

    private void hideContentView() {
        if (contentViews != null) {
            for (View contentView : contentViews) {
                contentView.setVisibility(GONE);
            }
        }
    }

    public boolean isContent() {
        return currentState == State.CONTENT;
    }

    private enum State {
        /**
         * 加载中
         */
        LOADING,
        /**
         * 加载成功 (空的情况自己处理)
         */
        CONTENT,
        /**
         * 加载失败
         */
        ERROR
    }


}
