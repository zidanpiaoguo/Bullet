package com.lzy.bulletproject.uhome.child.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseActivity;
import com.lzy.bulletproject.databinding.ActivityRclBinding;
import com.lzy.bulletproject.uhome.child.recyclerview.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bullet
 * @date 2019\2\18 0018.
 */

public class RclActivity extends BaseActivity<ActivityRclBinding> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcl);
        initView();
    }

    private void initView() {

        bindingView.rclMain.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i,"hao");
        }
        TestAdapter adapter = new TestAdapter(R.layout.item_rcl_three,list);
        bindingView.rclMain.setAdapter(adapter);
    }
}
