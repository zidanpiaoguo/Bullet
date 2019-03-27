package com.lzy.bulletproject.ushopping.view;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseFragment;
import com.lzy.bulletproject.databinding.FragmentChatBinding;
import com.lzy.bulletproject.databinding.FragmentShopBinding;
import com.lzy.bulletproject.umoney.view.MoneyFragment;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class ShopFragment extends BaseFragment<FragmentShopBinding> {

    private static ShopFragment mShopFragment;
    /**
     * 双重锁单例
     * @return
     */
    public static ShopFragment getFragment() {
        if (mShopFragment == null) {
            synchronized (ShopFragment.class) {
                if (mShopFragment == null) {
                    mShopFragment = new ShopFragment();
                }
            }
        }
        return mShopFragment;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_shop;
    }
}
