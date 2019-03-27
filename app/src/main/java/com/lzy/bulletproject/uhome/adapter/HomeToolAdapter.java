package com.lzy.bulletproject.uhome.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.bulletproject.R;
import com.lzy.bulletproject.uhome.data.ToolData;

import java.util.List;

/**
 * @author bullet
 * @date 2019\1\29 0029.
 */

public class HomeToolAdapter extends BaseQuickAdapter<ToolData,BaseViewHolder> {


    public HomeToolAdapter(int layoutResId, @Nullable List<ToolData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolData item) {
        helper.setText(R.id.tv_name,item.getName());
        ImageView imageView = helper.getView(R.id.iv_image);
        imageView.setImageResource(item.getImage());
        helper.addOnClickListener(R.id.rl_item);


    }
}
