package com.lzy.bulletproject.umine.view;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseFragment;
import com.lzy.bulletproject.databinding.FragmentChatBinding;
import com.lzy.bulletproject.databinding.FragmentMineBinding;
import com.lzy.bulletproject.uhome.view.HomeFragment;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class MineFragment extends BaseFragment<FragmentMineBinding> {

    private static MineFragment mMineFragment;
    /**
     * 双重锁单例
     * @return
     */
    public static MineFragment getFragment() {
        if (mMineFragment == null) {
            synchronized (MineFragment.class) {
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
            }
        }
        return mMineFragment;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }
}
