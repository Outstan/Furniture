package com.hongwei.basiclib.lifecycle;

import androidx.lifecycle.MutableLiveData;

import com.hongwei.basiclib.bean.DialogBean;

/**
 * FileName: DialogLiveData
 * Author: XYB
 * Date: 2021/1/5
 * Description:
 */
public final class DialogLiveData<T> extends MutableLiveData<T> {

    private DialogBean bean = new DialogBean();

    public void setValue(boolean isShow) {
        bean.setShow(isShow);
        bean.setMsg("");
        setValue((T) bean);
    }

    public void setValue(boolean isShow, String msg) {
        bean.setShow(isShow);
        bean.setMsg(msg);
        setValue((T) bean);
    }
}
