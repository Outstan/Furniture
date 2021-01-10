package com.hongwei.furniture.modules.main;

import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hongwei.basiclib.base.BaseNoModelActivity;
import com.hongwei.furniture.R;
import com.hongwei.furniture.databinding.ActivityMainBinding;
import com.hongwei.furniture.modules.main.home.ui.HomeFragment;
import com.hongwei.furniture.modules.main.me.ui.MeFragment;
import com.hongwei.furniture.modules.main.message.ui.MessageFragment;

public class MainActivity extends BaseNoModelActivity<ActivityMainBinding> implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;
    private FragmentManager manager;
    private HomeFragment mHomeFragment;
    private MeFragment mMeFragment;
    private MessageFragment mMessageFragment;
    private Fragment mCurrentFragment;
    private FragmentTransaction ft;

    @Override
    protected int onCreate() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRadioGroup = dataBinding.mainRadiogroup;

        manager = getSupportFragmentManager();
        mRadioGroup.setOnCheckedChangeListener(this);

        mHomeFragment = HomeFragment.getInstance();
        ft = manager.beginTransaction();
        ft.add(R.id.main_holder,mHomeFragment);
        mCurrentFragment = mHomeFragment;
        ft.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ft = manager.beginTransaction();
        ft.hide(mCurrentFragment);
        switch (checkedId) {
            case R.id.tab_home:
                if (mHomeFragment != null){
                    ft.show(mHomeFragment);
                }else {
                    mHomeFragment = HomeFragment.getInstance();
                    ft.add(R.id.main_holder,mHomeFragment);
                }
                mCurrentFragment = mHomeFragment;
                break;
            case R.id.tab_message:
                if (mMessageFragment != null){
                    ft.show(mMessageFragment);
                }else {
                    mMessageFragment = MessageFragment.getInstance();
                    ft.add(R.id.main_holder,mMessageFragment);
                }
                mCurrentFragment = mMessageFragment;
                break;
            case R.id.tab_me:
                if (mMeFragment != null){
                    ft.show(mMeFragment);
                }else {
                    mMeFragment = MeFragment.getInstance();
                    ft.add(R.id.main_holder,mMeFragment);
                }
                mCurrentFragment = mMeFragment;
                break;
        }
        ft.commit();
    }
}