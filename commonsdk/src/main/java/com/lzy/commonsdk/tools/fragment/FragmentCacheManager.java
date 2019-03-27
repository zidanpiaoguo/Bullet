package com.lzy.commonsdk.tools.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author bullet
 * @date 2019\1\21 0021.
 */

public class FragmentCacheManager {


    private static final String TAG = "FragmentCacheManager";

    /**
     * FragmentManager 的管理库（一个项目可能有多个FragmentManager）
     */
    private static Map<String, FragmentCacheManager> map = new HashMap<>();

    /**
     * fragment 储存栈
     */
    private Stack<Integer> mFragmentStack;

    /**
     * 缓存的Fragment集合数据
     */
    private SparseArray<FragmentInfo> mCacheFragment;
    /**
     * 当前展示的Fragment
     */
    private Fragment mCurrentFragment;
    private Fragment mOldFragment;
    private FragmentManager mFragmentManager;
    private Activity mActivity;
    private int mContainerId;
    private OnBootCallBackListener listener;

    private int mCurrentFragmentIndex = -1;

    private FragmentCacheManager() {
        mCacheFragment = new SparseArray<>();
        mFragmentStack = new Stack<>();
    }

    public static FragmentCacheManager getInstance(String name) {
        return getInstance(name, false);
    }

    /**
     * 这是根据name  来初始化数据，
     * 可能一个项目有多个Manager 来管理不同的Fangment 群组
     *
     * @param name    标识名字
     * @param isNewFg 是不是新的Manager
     * @return
     */
    public static FragmentCacheManager getInstance(String name, boolean isNewFg) {
        if (name == null) {
            name = FragmentCacheManager.class.getName();
            if (map.get(name) == null) {
                try {
                    map.put(name, (FragmentCacheManager) Class.forName(name).newInstance());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (map.get(name) == null) {
            map.put(name, new FragmentCacheManager());
        } else if (map.get(name) != null && isNewFg) {
            map.remove(name);
            map.put(name, new FragmentCacheManager());
        }
        return map.get(name);
    }

    /**
     * 是否已包含当前的Manager
     *
     * @param fgcm
     * @return
     */
    public static boolean isContentMG(FragmentCacheManager fgcm) {
        return map.containsValue(fgcm);
    }

    /**
     * 临时打开一个fragment
     * 我这里没有添加到回退栈 因为一般这样都是不需要回退的，需要的自行打开
     * <p>
     * 用于替换非常频繁的Fangment 布局中，每次都只打开一次
     *
     * @param fm        Fragment: getChildFragmentManager()  Activity : getSupportFragmentManager(),
     * @param mFragment fragment
     * @param args      Bundle
     * @param layoutId  R.layout.root
     */

    public static void replaceFragment(final FragmentManager fm, final Fragment mFragment, Bundle args, int layoutId) {

        //是否已经被添加
        if (mFragment.isAdded()) {
            return;
        }
        //是否需要传值
        if (args != null && !args.isEmpty()) {
            mFragment.setArguments(args);
        }

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                mFragment.onResume();
            }
        });
        fragmentTransaction.replace(layoutId, mFragment);

        //基本不用添加到回退栈  需要的自己打开
        // fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 如果Key==null 则默认删除初始化的FG
     *
     * @param key
     */
    public static void removeFG(String key) {
        if (key == null) {
            key = FragmentCacheManager.class.getName();
        }
        map.remove(key);
    }

    /**
     * 初始化， 当前的Activity中包含Fragment
     * <p>
     * 这个是为了传递出错直接把两个构造函数分开了 Activity 中的调用第一个，Fragment中的嗲用第二个
     *
     * @param activity    主类
     * @param containerId 记录用的标识
     */
    public void setUp(FragmentActivity activity, int containerId) {

        this.mActivity = activity;
        this.mContainerId = containerId;
        mFragmentManager = activity.getSupportFragmentManager();
    }

    /**
     * 初始化，如果是Fragment里的Fragment 数组，调用方法
     *
     * @param fragment    当前Fragment
     * @param containerId 当前的ID
     */
    public void setUp(Fragment fragment, int containerId) {
        //Fragment所在的Activity
        this.mActivity = fragment.getActivity();
        this.mContainerId = containerId;
        mFragmentManager = fragment.getChildFragmentManager();

    }

    /**
     * 获取当前的Fragment
     *
     * @return 当前Fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 设置当前的fragment
     *
     * @param index
     */
    public void setCurrentFragment(int index) {
        if (index < 0) {
            return;
        }
        if (index == mCurrentFragmentIndex) {
            return;
        }
        mFragmentStack.add(mCurrentFragmentIndex);
        FragmentInfo info = mCacheFragment.get(index);
        mCurrentFragmentIndex = index;
        goToThisFragment(info);
    }

    /**
     * 进入某一个fragment
     *
     * @param param
     */
    private void goToThisFragment(FragmentInfo param) {
        int containerId = mContainerId;
        Class<?> cls = param.mClass;
        if (cls == null) {
            return;
        }
        try {
            String fragmentTag = param.tag;
            //通过Tag查找活动的Fragment，相同到Fragment可以创建多个实力对象通过设置不同到Tag
            Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
            if (fragment == null) {
                //创建对象将数据传递给Fragment对象
                fragment = (Fragment) cls.newInstance();
                if (param.args != null) {
                    fragment.setArguments(param.args);
                }
                param.fragment = fragment;
            }
            if (mCurrentFragment != null && mCurrentFragment != fragment) {

                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment temp = mCurrentFragment;
                mOldFragment = temp;
                mCurrentFragment = fragment;
                if (!fragment.isAdded()) {
                    //因为commit 提交有报错，重叠
                    ft.hide(temp).add(containerId, fragment, fragmentTag).show(fragment).commitAllowingStateLoss();
                } else {
                    ft.hide(temp).show(fragment).commitAllowingStateLoss();
                }
            } else {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                if (!fragment.isAdded()) {
                    ft.add(containerId, fragment, fragmentTag);
                }
                mCurrentFragment = fragment;
                ft.commitAllowingStateLoss();
            }


        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新加入一个Fragment 类  并加入cache中,这个要在掉用 这个使用只要开始调用一次就可以了
     * 需要在 setCurrentFragment 之前调用
     *
     * @param index  Fragment对应的索引，通过索引找到对应的显示Fragment
     * @param mClass 需要创建的Fragment.class 文件
     */
    public void addFragmentToCache(int index, Class<?> mClass) {
        FragmentInfo info = createInfo(mClass.getName(), mClass, null);
        mCacheFragment.put(index, info);
    }

    private FragmentInfo createInfo(String tag, Class<?> clss, Bundle args) {
        return new FragmentInfo(tag, clss, args);
    }

    /**
     * 填加需要传参的不同名的 Fragment 类，
     *
     * @param index  Fragment对应的索引，通过索引找到对应的显示Fragment
     * @param mClass 需要创建的Fragment.class 文件
     * @param args   Bundle 会传递给生产的Fragment对象
     */
    public void addFragmentToCache(int index, Class<?> mClass, Bundle args) {
        FragmentInfo info = createInfo(mClass.getName(), mClass, args);
        mCacheFragment.put(index, info);
    }

    /**
     * 可以自定义tag  主要用于相同的Fragment 多次被添加 tag需要设置不同，
     *
     * @param index  Fragment对应的索引，通过索引找到对应的显示Fragment
     * @param mClass 需要创建的Fragment.class 文件
     * @param tag
     */
    public void addFragmentToCache(int index, Class<?> mClass, String tag) {
        FragmentInfo info = createInfo(tag, mClass, null);
        mCacheFragment.put(index, info);
    }

    /**
     * 要实现同一个对象多次创建必须通过不同的Tag来做唯一标示
     *
     * @param index Fragment对应的索引，通过索引找到对应的显示Fragment
     * @param clss  需要创建的Fragment.class 文件
     * @param tag   Fragment的唯一标示
     * @param args  Bundle 会传递给生产的Fragment对象，
     */
    public void addFragmentToCache(int index, Class<?> clss, String tag, Bundle args) {
        FragmentInfo info = createInfo(tag, clss, args);
        mCacheFragment.put(index, info);
    }

    /**
     * 进入上一个fragment
     */
    public void goToOldFragment() {

        if (mCurrentFragment != null && mOldFragment != null) {
            mFragmentManager.beginTransaction().detach(mCurrentFragment).commit();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment temp = mCurrentFragment;
            mCurrentFragment = mOldFragment;
            mOldFragment = temp;
            ft.hide(temp).show(mCurrentFragment).commit();
            // 隐藏当前的fragment，显示下一个
        }
    }

    /**
     * 返回上一个fragment
     *
     * @return
     */
    public int onBackPress() {

        if (mActivity.isTaskRoot()) {
            if (listener != null) {
                listener.onBootCallBack();
            }
            return doReturnBack();
        }
        return -2;
    }

    public int doReturnBack() {
        if (!mFragmentStack.empty()) {
            int back = mFragmentStack.pop();
            if (back >= 0) {
                setBackFragment(back);
            } else {
                mActivity.finish();
            }
            return back;
        } else {
            mActivity.finish();
            return 0;
        }
    }

    public void setBackFragment(int index) {
        if (index < 0) {
            return;
        }

        if (index == mCurrentFragmentIndex) {
            return;
        }
        FragmentInfo info = mCacheFragment.get(index);
        mCurrentFragmentIndex = index;
        goToThisFragment(info);
    }

    public void setListener(OnBootCallBackListener listener) {
        this.listener = listener;
    }

    public interface OnBootCallBackListener {
        /**
         * 返回键事件触发
         */
        void onBootCallBack();
    }

    /**
     * fragment 的数据
     */
    static final class FragmentInfo {
        private final String tag;
        private final Class<?> mClass;
        private final Bundle args;
        Fragment fragment;

        FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            mClass = _class;
            args = _args;
        }
    }

}