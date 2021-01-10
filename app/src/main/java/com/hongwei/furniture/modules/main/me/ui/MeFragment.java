package com.hongwei.furniture.modules.main.me.ui;

import com.hongwei.basiclib.base.BaseNoModelFragment;
import com.hongwei.furniture.R;
import com.hongwei.furniture.databinding.FragmentMeBinding;

/**
 * FileName: MeFragment
 * Author: XYB
 * Date: 2021/1/10
 * Description:
 */
public class MeFragment extends BaseNoModelFragment<FragmentMeBinding> {
    @Override
    protected int onCreate() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static MeFragment getInstance(){
        return new MeFragment();
    }
}
