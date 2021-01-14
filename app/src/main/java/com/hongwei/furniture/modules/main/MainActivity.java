package com.hongwei.furniture.modules.main;

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
    private FragmentTransaction ft;
    private Fragment mCurrentFragment;

    @Override
    protected int onCreate() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRadioGroup = dataBinding.mainRadiogroup;
        mHomeFragment = HomeFragment.getInstance();
        mMessageFragment = MessageFragment.getInstance();
        mMeFragment = MeFragment.getInstance();

        manager = getSupportFragmentManager();
        mRadioGroup.setOnCheckedChangeListener(this);

        SwitchFragment(mHomeFragment);
    }

    @Override
    protected void initData() {

    }

    /**
     * RadioGroup 点击监听
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.tab_home:
                SwitchFragment(mHomeFragment);
                break;
            case R.id.tab_message:
                SwitchFragment(mMessageFragment);
                break;
            case R.id.tab_me:
                SwitchFragment(mMeFragment);
                break;
        }
    }

    /**
     * fragment切换
     *
     * @param targetFragment
     */
    private void SwitchFragment(Fragment targetFragment) {
        ft = manager.beginTransaction();
        if (mCurrentFragment != null) {
            if (mCurrentFragment == targetFragment)
                return;
            ft.hide(mCurrentFragment);
        }
        //isAdded() 如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        if (targetFragment.isAdded()) {
            ft.show(targetFragment);
        } else {
            ft.add(R.id.main_holder, targetFragment, targetFragment.getClass().getName());
        }
        mCurrentFragment = targetFragment;
        ft.commit();
    }

}