package com.lzy.bulletproject.umoney.view;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseFragment;
import com.lzy.bulletproject.databinding.FragmentChatBinding;
import com.lzy.bulletproject.databinding.FragmentMoneyBinding;
import com.lzy.bulletproject.uhome.view.HomeFragment;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class MoneyFragment extends BaseFragment<FragmentMoneyBinding> {

    private static MoneyFragment mMoneyFragment;
    /**
     * 双重锁单例
     * @return
     */
    public static MoneyFragment getFragment() {
        if (mMoneyFragment == null) {
            synchronized (MoneyFragment.class) {
                if (mMoneyFragment == null) {
                    mMoneyFragment = new MoneyFragment();
                }
            }
        }
        return mMoneyFragment;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_money;
    }
}
