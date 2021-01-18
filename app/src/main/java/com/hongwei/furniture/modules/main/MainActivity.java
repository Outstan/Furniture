package com.hongwei.furniture.modules.main;

import android.Manifest;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hongwei.basiclib.base.BaseNoModelActivity;
import com.hongwei.basiclib.permission.PermissionFail;
import com.hongwei.basiclib.permission.PermissionHelper;
import com.hongwei.basiclib.permission.PermissionSuccess;
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
    public final int PERMISSION_REQUEST_CODE = 0x01;
    private String[] mPermissions = new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.USE_SIP, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

        /**
         * 权限申请
         */
        PermissionHelper.with(this).requestCode(PERMISSION_REQUEST_CODE).requestPerssion(mPermissions).request();
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

    /**
     * 申请的权限全都授予了
     */
    @PermissionSuccess(requestCode = PERMISSION_REQUEST_CODE)
    private void PermissionSuccess() {

    }
    /**
     * 申请的权限没全授予
     */
    @PermissionFail(requestCode = PERMISSION_REQUEST_CODE)
    private void PermissionFail(){

    }
}