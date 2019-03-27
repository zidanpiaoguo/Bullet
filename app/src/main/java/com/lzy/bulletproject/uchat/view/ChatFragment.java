package com.lzy.bulletproject.uchat.view;

import com.lzy.bulletproject.R;
import com.lzy.bulletproject.base.mvvm.BaseFragment;
import com.lzy.bulletproject.databinding.FragmentChatBinding;
import com.lzy.bulletproject.databinding.FragmentHomeBinding;

/**
 * @author bullet
 * @date 2019\1\22 0022.
 */

public class ChatFragment extends BaseFragment<FragmentChatBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_chat;
    }

    /**
     * 静态内部类，单例模式
     */
    private static class Single{
        private static ChatFragment chatFragment = new ChatFragment();
    }
    public static ChatFragment getFragment(){
        return Single.chatFragment;
    }




}
