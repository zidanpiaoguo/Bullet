package com.lzy.bulletproject.uhome.child.recyclerview.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author bullet
 * @date 2019\2\20 0020.
 */

public class TestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
