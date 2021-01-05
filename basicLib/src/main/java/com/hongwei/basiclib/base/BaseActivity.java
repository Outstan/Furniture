package com.hongwei.basiclib.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.hongwei.basiclib.bean.DialogBean;
import com.hongwei.basiclib.lifecycle.BaseViewModel;


/**
 * FileName: BaseActivity
 * Author: XYB
 * Date: 2021/1/5
 * Description:TODO ViewModel、ViewDataBinding都需要的基类
 */

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding>
        extends BaseNoModelActivity<DB> {

    protected VM viewModel;

    @Override
    protected DB initDataBinding(int layoutId) {
        DB db = super.initDataBinding(layoutId);
        /**
         * 将这两个初始化函数插在{@link BaseActivity#initDataBinding}
         */
        viewModel = initViewModel();
        initObserve();
        return db;
    }

    /**
     * 初始化ViewModel
     */
    protected abstract VM initViewModel();

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private void initObserve() {
        if (viewModel == null) return;
        viewModel.getShowDialog(this, new Observer<DialogBean>() {
            @Override
            public void onChanged(DialogBean bean) {
                if (bean.isShow()) {
                    showDialog(bean.getMsg());
                } else {
                    dismissDialog();
                }
            }
        });
        viewModel.getError(this, new Observer<Object>() {
            @Override
            public void onChanged(Object obj) {
                showError(obj);
            }
        });
    }

    /**
     * ViewModel层发生了错误
     */
    protected abstract void showError(Object obj);
}
