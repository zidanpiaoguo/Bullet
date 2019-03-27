package com.lzy.bulletproject.uhome.model;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.uhome.data.ToolData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bullet
 * @date 2019\1\29 0029.
 */

public class HomeModel {




    public List<ToolData> getToolData(){

        List<ToolData> list = new ArrayList<>();
        list.add(new ToolData("储存", R.mipmap.ic_home_icon1));
        list.add(new ToolData("记账本",R.mipmap.ic_home_icon2));
        list.add(new ToolData("借呗", R.mipmap.ic_home_icon3));
        list.add(new ToolData("花呗",R.mipmap.ic_home_icon4));
        list.add(new ToolData("充值中心", R.mipmap.ic_home_icon5));
        list.add(new ToolData("余额宝",R.mipmap.ic_home_icon6));
        list.add(new ToolData("芝麻信用", R.mipmap.ic_home_icon7));
        list.add(new ToolData("转账",R.mipmap.ic_home_icon8));
        list.add(new ToolData("共享单车",R.mipmap.ic_home_icon9));
        list.add(new ToolData("更多",R.mipmap.ic_home_more));
        return  list;
    }


}
