package com.lzy.bulletproject.uhome.child.recyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.bulletproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bullet
 * @date 2019\2\20 0020.
 */

public class OrderView extends RelativeLayout {
    private Context mContext;
    private View view;
    private List<String> listLeft = new ArrayList<>();
    private List<String> listRight = new ArrayList<>();


    public OrderView(Context context) {
        super(context);
    }

    public OrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public OrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }
    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_item_order,this);

        initData();
        addLeftView();
        addRightView();


    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            listLeft.add(i,"我的第"+i+"行");
        }
        for (int i = 0; i < 12; i++) {
            listRight.add(i,"我的第"+i+"行");
        }

    }

    private void addRightView() {
        LinearLayout rightView = view.findViewById(R.id.ll_right_content);

        for (int i = 0; i < listRight.size(); i++) {
            View childView = LayoutInflater.from(mContext).inflate(R.layout.view_item_order_child,null);
            TextView tvChild = childView.findViewById(R.id.tv_child_content);
            tvChild.setText(listRight.get(i));
            rightView.addView(childView);
        }
    }

    private void addLeftView() {
        LinearLayout leftView = view.findViewById(R.id.ll_left_content);

        for (int i = 0; i < listLeft.size(); i++) {
            View childView = LayoutInflater.from(mContext).inflate(R.layout.view_item_order_child,null);
            TextView tvChild = childView.findViewById(R.id.tv_child_content);
            tvChild.setText(listLeft.get(i));
            leftView.addView(childView);
        }
    }


}
