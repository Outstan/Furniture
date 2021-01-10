package com.hongwei.furniture.modules.main.message.ui;

import com.hongwei.basiclib.base.BaseNoModelFragment;
import com.hongwei.furniture.R;
import com.hongwei.furniture.databinding.FragmentMessageBinding;

/**
 * FileName: MessageFragment
 * Author: XYB
 * Date: 2021/1/10
 * Description:
 */
public class MessageFragment extends BaseNoModelFragment<FragmentMessageBinding> {
    @Override
    protected int onCreate() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static MessageFragment getInstance(){
        return new MessageFragment();
    }
}
